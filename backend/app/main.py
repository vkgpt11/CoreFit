import os
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from app.api.routes import router
from app.db import init_db

app = FastAPI(title="CoreFit API", version="0.2.0")
origins = [x.strip() for x in os.getenv("CORS_ORIGINS", "http://localhost:5173").split(",") if x.strip()]
app.add_middleware(CORSMiddleware, allow_origins=origins, allow_credentials=False, allow_methods=["GET","POST","PUT","DELETE"], allow_headers=["Content-Type","X-Admin-Token"])
app.include_router(router, prefix="/api/v1")

@app.on_event("startup")
def startup() -> None:
    init_db()

@app.get("/health")
def health() -> dict[str, str]:
    return {"status": "ok"}
