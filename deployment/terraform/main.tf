terraform {
  required_version = ">= 1.7.0"
  required_providers {
    google = { source = "hashicorp/google", version = "~> 6.0" }
  }
}

provider "google" {
  project = var.project_id
  region  = var.region
}

resource "google_storage_bucket" "media" {
  name                        = "${var.project_id}-corefit-media"
  location                    = var.region
  uniform_bucket_level_access = true
  force_destroy               = false
  versioning { enabled = true }
}

resource "google_artifact_registry_repository" "apps" {
  location      = var.region
  repository_id = "corefit"
  format        = "DOCKER"
}

resource "google_cloud_run_v2_service" "backend" {
  name     = "corefit-backend"
  location = var.region
  template {
    containers {
      image = var.backend_image
      env { name = "MEDIA_BUCKET" value = google_storage_bucket.media.name }
    }
  }
}

resource "google_cloud_run_v2_service" "admin_web" {
  name     = "corefit-admin-web"
  location = var.region
  template {
    containers { image = var.admin_image }
  }
}
