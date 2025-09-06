
# 1. Record architecture decisions
Date: 2025-09-03

## Status
Accepted

## Context
We maintain lightweight ADRs for decision traceability.

## Decision
Use Spring Boot WebFlux backend, Next.js FE, Terraform for AWS deploy, and a test pyramid (L0/L1/L2).

## Consequences
Enables reactive stack, clean CI/CD, and reproducible infra.
