
variable "public_key_path" {
  description = "Path to your public key for EC2 SSH (e.g., ~/.ssh/id_rsa.pub)"
  type        = string
}

variable "enable_backend_https" {
  description = "Enable ALB + HTTPS for backend"
  type        = bool
  default     = false
}

variable "acm_certificate_arn" {
  description = "ACM certificate ARN for HTTPS on ALB (required if enable_backend_https=true)"
  type        = string
  default     = ""
}

variable "aws_region" {
  description = "AWS region"
  type        = string
  default     = "us-east-1"
}
