
# Installation & Setup Guide

## Backend
```bash
cd backend
./mvnw -q -DskipTests package
./mvnw -q -Dspring-boot.run.profiles=local -Dcodeforge.mongo.enabled=false spring-boot:run
# http://localhost:8080
```

## Frontend
```bash
cd frontend
npm install
npm run dev
# http://localhost:3000
```

## Mobile
```bash
cd mobile
npm install
npx expo start
```

## Connectivity
- GET /api/ping
- GET /api/diag
- POST /api/test/fixtures/seed|purge
- GET /api/test/fixtures/list
