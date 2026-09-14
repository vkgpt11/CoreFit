import os
from datetime import timedelta
from google.cloud import storage

BUCKET_NAME = os.environ.get("MEDIA_BUCKET", "")


def create_upload_url(exercise_id: str, filename: str, content_type: str) -> dict[str, str]:
    if not BUCKET_NAME:
        raise RuntimeError("MEDIA_BUCKET is not configured")

    safe_name = filename.replace("/", "_")
    object_name = f"exercises/{exercise_id}/{safe_name}"

    client = storage.Client()
    blob = client.bucket(BUCKET_NAME).blob(object_name)
    url = blob.generate_signed_url(
        version="v4",
        expiration=timedelta(minutes=15),
        method="PUT",
        content_type=content_type,
    )
    return {"upload_url": url, "object_name": object_name}
