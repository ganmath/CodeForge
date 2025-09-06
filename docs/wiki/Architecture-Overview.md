
# Architecture Overview

- FE: Next.js → BE
- BE: Spring Boot WebFlux → Mongo Atlas, Kafka optional
- Mobile: React Native skeleton
- IaC: Terraform (S3+CloudFront, EC2+ALB)

```mermaid
flowchart LR
  UI[Next.js] --> API[Spring Boot]
  Mobile --> API
  API --> DB[(Mongo Atlas)]
  API --> K[Kafka (optional)]
  API --> X[External APIs (stubs)]
```
