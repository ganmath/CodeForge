
# Deployment Guide

## Terraform
- FE → S3+CloudFront (HTTPS via default cert)
- BE → EC2 (HTTP 8080) or ALB+HTTPS if `enable_backend_https=true` and `acm_certificate_arn` provided

### Commands
```bash
cd terraform
terraform init
terraform apply -var="public_key_path=~/.ssh/id_rsa.pub"
```

Outputs:
- `frontend_bucket`
- `cloudfront_domain`
- `backend_public_ip`
- `alb_dns` (if HTTPS enabled)
