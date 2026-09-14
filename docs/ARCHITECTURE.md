# CoreFit platform structure

CoreFit is a modular monorepo.

- `mobile/` — Android application (Kotlin + Jetpack Compose). Existing Android source will migrate here.
- `admin-web/` — React admin console for managing exercises and uploading media.
- `backend/` — Python/FastAPI API deployed to Google Cloud Run.
- `deployment/` — GCP infrastructure and deployment configuration.
- `docs/` — requirements, architecture and media/content guidance.

## Runtime architecture

```text
Admin browser (React) ───────┐
                             ├── HTTPS ──> Python API (Cloud Run) ──> PostgreSQL metadata
Android CoreFit app ─────────┘                       │
                                                    ├── signed upload/read URLs
                                                    ▼
                                           Google Cloud Storage
                                                    │
                                      optional event processing
                                                    ▼
                                           Cloud Run Functions
```

### Media flow

1. Admin creates/edits an exercise in Admin Web.
2. Admin Web asks Backend for a signed upload URL.
3. Browser uploads image/video directly to Cloud Storage.
4. Backend stores media metadata/version against the exercise.
5. Mobile requests the exercise catalog from Backend.
6. Mobile downloads media from the server-provided URL and caches it in app-local storage.
7. On future sessions, cached content is reused until its version/ETag changes.

Do not route large image/video bytes through the Python API. The API should authorize the operation and issue signed URLs.

### GCP mapping

| AWS idea | GCP implementation |
|---|---|
| S3 | Google Cloud Storage |
| Lambda for HTTP API | Cloud Run service (preferred) |
| Lambda for async events | Cloud Run functions |
| RDS PostgreSQL | Cloud SQL for PostgreSQL |
| CloudFront | Cloud CDN (optional when scale warrants it) |
| ECR | Artifact Registry |

The HTTP backend is a normal stateless Cloud Run service; a separate function is not invoked for every API request. Event-driven functions are reserved for media validation/transcoding/thumbnails and similar background work.
