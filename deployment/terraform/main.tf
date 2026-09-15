terraform {
  required_version = ">= 1.7.0"
  required_providers { google = { source = "hashicorp/google", version = "~> 6.0" } }
}
provider "google" { project = var.project_id region = var.region }
resource "google_project_service" "apis" { for_each = toset(["run.googleapis.com","artifactregistry.googleapis.com","storage.googleapis.com","sqladmin.googleapis.com","secretmanager.googleapis.com"]) service = each.value disable_on_destroy = false }
resource "google_storage_bucket" "media" { name = "${var.project_id}-corefit-media" location = var.region uniform_bucket_level_access = true force_destroy = false versioning { enabled = true } cors { origin = var.admin_origins method = ["GET","PUT","HEAD"] response_header = ["Content-Type","ETag"] max_age_seconds = 3600 } }
resource "google_sql_database_instance" "postgres" { name = "corefit-postgres" region = var.region database_version = "POSTGRES_16" deletion_protection = true settings { tier = var.db_tier backup_configuration { enabled = true point_in_time_recovery_enabled = true } } depends_on = [google_project_service.apis] }
resource "google_sql_database" "corefit" { name = "corefit" instance = google_sql_database_instance.postgres.name }
resource "google_sql_user" "app" { name = "corefit" instance = google_sql_database_instance.postgres.name password = var.db_password }
resource "google_artifact_registry_repository" "apps" { location = var.region repository_id = "corefit" format = "DOCKER" depends_on = [google_project_service.apis] }
resource "google_cloud_run_v2_service" "backend" { name = "corefit-backend" location = var.region template { containers { image = var.backend_image env { name="MEDIA_BUCKET" value=google_storage_bucket.media.name } env { name="DATABASE_URL" value="postgresql+psycopg://corefit:${urlencode(var.db_password)}@/${google_sql_database.corefit.name}?host=/cloudsql/${google_sql_database_instance.postgres.connection_name}" } env { name="ADMIN_TOKEN" value=var.admin_token } env { name="CORS_ORIGINS" value=join(",",var.admin_origins) } } volumes { name="cloudsql" cloud_sql_instance { instances=[google_sql_database_instance.postgres.connection_name] } } } depends_on=[google_project_service.apis] }
resource "google_cloud_run_v2_service" "admin_web" { name="corefit-admin-web" location=var.region template { containers { image=var.admin_image } } depends_on=[google_project_service.apis] }
