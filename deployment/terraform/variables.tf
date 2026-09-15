variable "project_id" { type=string }
variable "region" { type=string default="asia-south1" }
variable "backend_image" { type=string }
variable "admin_image" { type=string }
variable "db_tier" { type=string default="db-f1-micro" }
variable "db_password" { type=string sensitive=true }
variable "admin_token" { type=string sensitive=true }
variable "admin_origins" { type=list(string) default=["http://localhost:5173"] }
