
resource "aws_s3_bucket" "fe_bucket" {
  bucket_prefix = "codeforge-fe-"
  force_destroy = true
}

resource "aws_s3_bucket_website_configuration" "fe_site" {
  bucket = aws_s3_bucket.fe_bucket.id
  index_document {
    suffix = "index.html"
  }
  error_document {
    key = "index.html"
  }
}

resource "aws_s3_bucket_public_access_block" "fe_public_access" {
  bucket                  = aws_s3_bucket.fe_bucket.id
  block_public_acls       = true
  block_public_policy     = true
  ignore_public_acls      = true
  restrict_public_buckets = true
}

# CloudFront (default cert for *.cloudfront.net)
resource "aws_cloudfront_origin_access_control" "oac" {
  name                              = "oac-codeforge-fe"
  description                       = "OAC for S3 origin"
  origin_access_control_origin_type = "s3"
  signing_behavior                  = "always"
  signing_protocol                  = "sigv4"
}

resource "aws_cloudfront_distribution" "fe_cdn" {
  enabled             = true
  default_root_object = "index.html"

  origin {
    domain_name              = aws_s3_bucket.fe_bucket.bucket_regional_domain_name
    origin_id                = "s3-fe-origin"
    origin_access_control_id = aws_cloudfront_origin_access_control.oac.id
  }

  default_cache_behavior {
    target_origin_id       = "s3-fe-origin"
    viewer_protocol_policy = "redirect-to-https"
    allowed_methods        = ["GET", "HEAD"]
    cached_methods         = ["GET", "HEAD"]
    forwarded_values {
      query_string = false
      cookies { forward = "none" }
    }
  }

  restrictions {
    geo_restriction { restriction_type = "none" }
  }

  viewer_certificate {
    cloudfront_default_certificate = true
  }
}

output "frontend_bucket" { value = aws_s3_bucket.fe_bucket.id }
output "cloudfront_domain" { value = aws_cloudfront_distribution.fe_cdn.domain_name }
