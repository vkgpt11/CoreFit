from __future__ import annotations

import os
import re
from datetime import timedelta
from pathlib import Path
from uuid import uuid4
from google.cloud import storage

BUCKET_NAME = os.getenv("MEDIA_BUCKET", "")
ALLOWED_TYPES = {"image/jpeg", "image/png", "image/webp", "video/mp4", "video/webm"}
MAX_FILENAME = 180

def _safe(value: str) -> str:
    return re.sub(r"[^a-zA-Z0-9_.-]", "_", value)[:MAX_FILENAME]

def create_upload_url(exercise_id: str, filename: str, content_type: str) -> dict[str, str]:
    if not BUCKET_NAME:
        raise RuntimeError("MEDIA_BUCKET is not configured")
    if content_type not in ALLOWED_TYPES:
        raise ValueError("Unsupported media type")
    safe_exercise = _safe(exercise_id)
    safe_name = _safe(Path(filename).name)
    object_name = f"exercises/{safe_exercise}/{uuid4().hex}-{safe_name}"
    client = storage.Client()
    blob = client.bucket(BUCKET_NAME).blob(object_name)
    url = blob.generate_signed_url(version="v4", expiration=timedelta(minutes=15), method="PUT", content_type=content_type)
    return {"upload_url": url, "object_name": object_name}

def verify_object(object_name: str) -> dict[str, object]:
    client = storage.Client()
    blob = client.bucket(BUCKET_NAME).blob(object_name)
    blob.reload()
    if blob.content_type not in ALLOWED_TYPES:
        raise ValueError("Uploaded object has unsupported content type")
    max_bytes = int(os.getenv("MAX_MEDIA_BYTES", str(50 * 1024 * 1024)))
    if (blob.size or 0) > max_bytes:
        blob.delete()
        raise ValueError("Uploaded object exceeds size limit")
    return {"url": f"https://storage.googleapis.com/{BUCKET_NAME}/{object_name}", "size": blob.size or 0, "content_type": blob.content_type or ""}
