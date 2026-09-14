# CoreFit

CoreFit is a guided exercise platform with a modular monorepo architecture.

## Components

- `mobile/android/` — Android app built with Kotlin + Jetpack Compose.
- `admin-web/` — React admin console for exercise/content management and media upload.
- `backend/` — Python/FastAPI API deployed to Google Cloud Run.
- `deployment/` — GCP infrastructure and deployment configuration.
- `docs/` — architecture, requirements, and exercise-media guidance.

## GCP architecture

- Google Cloud Storage stores exercise images and videos.
- Cloud Run hosts the Python backend and React admin web app.
- Cloud Run functions are reserved for asynchronous media processing when needed.
- Cloud SQL for PostgreSQL is the intended metadata store.
- Artifact Registry stores deployable container images.
- GitHub Actions builds/tests each component and provides a manual GCP deployment workflow.

For details, see `docs/ARCHITECTURE.md`.

## Media flow

Admins use the React console to request a short-lived signed upload URL from the backend and upload images/videos directly to Cloud Storage. The mobile app receives media URLs and version metadata from the backend, downloads media, and caches it locally for offline-friendly reuse.

## Current migration note

The existing Android project has been copied into `mobile/android/` as the new modular location. Legacy root Android files are temporarily retained until the modular CI path is verified, after which they can be removed safely.
