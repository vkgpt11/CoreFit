from __future__ import annotations

import os
from fastapi import APIRouter, Depends, Header, HTTPException
from pydantic import BaseModel, Field
from sqlalchemy.orm import Session
from app.db import Exercise, SessionLocal
from app.services.storage import create_upload_url, verify_object

router = APIRouter()
ADMIN_TOKEN = os.getenv("ADMIN_TOKEN", "")

def db_session():
    db = SessionLocal()
    try: yield db
    finally: db.close()

def admin(x_admin_token: str | None = Header(default=None)) -> None:
    if not ADMIN_TOKEN or x_admin_token != ADMIN_TOKEN:
        raise HTTPException(401, "Admin authentication required")

class ExerciseIn(BaseModel):
    id: str = Field(pattern=r"^[a-z0-9_\-]+$")
    name: str
    category: str
    description: str = ""
    reps: int = Field(default=10, ge=1, le=200)
    sets: int = Field(default=1, ge=1, le=20)
    move_seconds: int = Field(default=2, ge=1, le=120)
    hold_seconds: int = Field(default=0, ge=0, le=600)
    rest_seconds: int = Field(default=20, ge=0, le=600)
    published: bool = False

class UploadRequest(BaseModel):
    exercise_id: str
    filename: str
    content_type: str

class CompleteUpload(BaseModel):
    exercise_id: str
    object_name: str

def dto(e: Exercise) -> dict:
    return {c.name: getattr(e, c.name) for c in e.__table__.columns}

@router.get("/exercises")
def list_exercises(db: Session = Depends(db_session)) -> dict:
    return {"items": [dto(e) for e in db.query(Exercise).filter(Exercise.published.is_(True)).order_by(Exercise.category, Exercise.name).all()]}

@router.get("/admin/exercises", dependencies=[Depends(admin)])
def admin_exercises(db: Session = Depends(db_session)) -> dict:
    return {"items": [dto(e) for e in db.query(Exercise).order_by(Exercise.category, Exercise.name).all()]}

@router.put("/admin/exercises/{exercise_id}", dependencies=[Depends(admin)])
def upsert_exercise(exercise_id: str, body: ExerciseIn, db: Session = Depends(db_session)) -> dict:
    if exercise_id != body.id: raise HTTPException(400, "Path id must match body id")
    exercise = db.get(Exercise, exercise_id) or Exercise(id=exercise_id)
    for key, value in body.model_dump().items(): setattr(exercise, key, value)
    db.add(exercise); db.commit(); db.refresh(exercise)
    return dto(exercise)

@router.delete("/admin/exercises/{exercise_id}", dependencies=[Depends(admin)])
def delete_exercise(exercise_id: str, db: Session = Depends(db_session)) -> dict:
    exercise = db.get(Exercise, exercise_id)
    if not exercise: raise HTTPException(404, "Exercise not found")
    db.delete(exercise); db.commit()
    return {"deleted": True}

@router.post("/admin/media/upload-url", dependencies=[Depends(admin)])
def get_upload_url(request: UploadRequest) -> dict[str, str]:
    try: return create_upload_url(request.exercise_id, request.filename, request.content_type)
    except (RuntimeError, ValueError) as exc: raise HTTPException(400, str(exc)) from exc

@router.post("/admin/media/complete", dependencies=[Depends(admin)])
def complete_upload(request: CompleteUpload, db: Session = Depends(db_session)) -> dict:
    exercise = db.get(Exercise, request.exercise_id)
    if not exercise: raise HTTPException(404, "Exercise not found")
    try: media = verify_object(request.object_name)
    except (RuntimeError, ValueError) as exc: raise HTTPException(400, str(exc)) from exc
    exercise.media_url = str(media["url"]); exercise.media_version += 1
    db.commit(); db.refresh(exercise)
    return {"exercise": dto(exercise), "media": media}
