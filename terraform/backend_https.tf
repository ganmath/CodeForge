
# Optional ALB + HTTPS
resource "aws_lb" "alb" {
  count               = var.enable_backend_https ? 1 : 0
  name                = "codeforge-alb"
  internal            = false
  load_balancer_type  = "application"
  security_groups     = [aws_security_group.be_sg.id]
  subnets             = data.aws_subnets.default.ids
}

data "aws_vpc" "default" {
  default = true
}

data "aws_subnets" "default" {
  filter {
    name   = "vpc-id"
    values = [data.aws_vpc.default.id]
  }
}

resource "aws_lb_target_group" "tg" {
  count    = var.enable_backend_https ? 1 : 0
  name     = "codeforge-tg"
  port     = 8080
  protocol = "HTTP"
  vpc_id   = data.aws_vpc.default.id
  health_check {
    path = "/actuator/health"
  }
}

resource "aws_lb_listener" "https" {
  count               = var.enable_backend_https ? 1 : 0
  load_balancer_arn   = aws_lb.alb[0].arn
  port                = 443
  protocol            = "HTTPS"
  ssl_policy          = "ELBSecurityPolicy-2016-08"
  certificate_arn     = var.acm_certificate_arn
  default_action {
    type             = "forward"
    target_group_arn = aws_lb_target_group.tg[0].arn
  }
}

resource "aws_lb_target_group_attachment" "attach" {
  count            = var.enable_backend_https ? 1 : 0
  target_group_arn = aws_lb_target_group.tg[0].arn
  target_id        = aws_instance.backend.id
  port             = 8080
}

output "alb_dns" {
  value       = var.enable_backend_https ? aws_lb.alb[0].dns_name : null
  description = "ALB DNS if HTTPS enabled"
}
