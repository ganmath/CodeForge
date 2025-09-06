
# Capabilities, Features & Stories

## Epic 1: Connectivity
- Feature: Ping API
  - Story: Call /api/ping, see {status:ok}
  - SP: 1
  - Acceptance: 200 OK, status=ok
- Feature: Diagnostics
  - Story: /api/diag shows flags
  - SP: 2
  - Acceptance: JSON includes mongo,kafka,external
- Feature: Fixtures
  - Story: Seed/purge/list tenants
  - SP: 3
  - Acceptance: Endpoints work as described

## Epic 2: Frontend Integration
- Feature: Ping button
  - Story: Click in FE → see API response
  - SP: 2
  - Acceptance: Response rendered

## Epic 3: Mobile Integration
- Feature: Ping screen
  - Story: Press in mobile → see API response
  - SP: 2
  - Acceptance: Response shown

## Epic 4: CI/CD & Quality
- Feature: L0 Context (SP:1)
- Feature: L1 API BDD (SP:3)
- Feature: L2 UI BDD (SP:5)
- Feature: SonarCloud Scan (SP:2)

## Epic 5: Infrastructure
- Feature: Terraform Infra (SP:5)
