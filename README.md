# CodeForge

Spring Boot CRUD template with full-stack implementation, testing, and AWS deployment.

## Quick Start (Local)
### Backend (Java 21, Spring Boot 3 WebFlux)
```bash
cd backend
./mvnw -q -DskipTests package
./mvnw -q -Dspring-boot.run.profiles=local -Dcodeforge.mongo.enabled=false spring-boot:run
# http://localhost:8080/api/ping
```
### Frontend (Next.js 14, TypeScript)
```bash
cd frontend
npm install
npm run dev
# http://localhost:3000
```
### Mobile (React Native / Expo skeleton)
```bash
cd mobile
npm install
npx expo start
```

## Tests
- **L0:** Spring context sanity
- **L1:** API BDD (Cucumber + REST-assured)
- **L2:** UI BDD (Cucumber + Selenide headless Chrome)
```bash
cd backend
./mvnw -q -Dcodeforge.mongo.enabled=false -Dtest="com.codeforge.L0_ContextLoadsTest,bdd.l1.RunL1CucumberTest" test
./mvnw -q -Dselenide.headless=true -Dtest="bdd.l2.RunL2CucumberTest" test
```

## Endpoints
- `GET /api/ping` -> `{status:"ok", ts:...}`
- `GET /api/diag` -> connectivity flags `{ mongo, kafka, external }`
- `POST /api/test/fixtures/seed|list|purge`

## Terraform (AWS)
See `terraform/` and the Wiki Deployment Guide.