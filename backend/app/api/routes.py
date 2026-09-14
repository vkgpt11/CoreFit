from fastapi import APIRouter
from pydantic import BaseModel
from app.services.storage import create_upload_url

router = APIRouter()

class UploadRequest(BaseModel):
    exercise_id: str
    filename: str
    content_type: str

@router.get("/exercises")
def list_exercises() -> dict:
    # Replace with Cloud SQL repository in the next step.
    return {"items": []}

@router.post("/admin/media/upload-url")
def get_upload_url(request: UploadRequest) -> dict[str, str]:
    return create_upload_url(
        exercise_id=request.exercise_id,
        filename=request.filename,
        content_type=request.content_type,
    )
