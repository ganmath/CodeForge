# CodeForge

Full-stack development template with Spring Boot backend, Next.js frontend, and AWS deployment.

## Prerequisites
- **Java 21** (OpenJDK or Oracle)
- **Node.js 20+** with npm
- **Git** for version control

## Quick Start (Local)

### Backend (Java 21, Spring Boot 3 WebFlux)

**Windows (PowerShell):**
```powershell
cd backend
.\mvnw.cmd -q -DskipTests package
.\mvnw.cmd -q spring-boot:run "-Dspring-boot.run.profiles=local" "-Dcodeforge.mongo.enabled=false"
```

**Unix/Linux/macOS:**
```bash
cd backend
./mvnw -q -DskipTests package
./mvnw -q spring-boot:run -Dspring-boot.run.profiles=local -Dcodeforge.mongo.enabled=false
```

**Verify:** http://localhost:8080/api/ping

### Frontend (Next.js 14, TypeScript)
```bash
cd frontend
npm install
npm run dev
```
**Verify:** http://localhost:3000

### Mobile (React Native / Expo skeleton)
```bash
cd mobile
npm install
npx expo start
```

## Testing

### Test Levels
- **L0:** Spring context sanity check
- **L1:** API BDD tests (Cucumber + REST-assured)
- **L2:** UI BDD tests (Cucumber + Selenide headless Chrome)

### Running Tests

**Windows (PowerShell/CMD):**
```cmd
cd backend
# L0 + L1 tests (backend only)
cmd /c mvnw.cmd -q -Dcodeforge.mongo.enabled=false -Dtest="com.codeforge.L0_ContextLoadsTest,bdd.l1.RunL1CucumberTest" test

# L2 UI tests (requires frontend running on port 3000)
cmd /c mvnw.cmd -q -Dselenide.headless=true -Dtest="bdd.l2.RunL2CucumberTest" test
```

**Unix/Linux/macOS:**
```bash
cd backend
# L0 + L1 tests (backend only)
./mvnw -q -Dcodeforge.mongo.enabled=false -Dtest="com.codeforge.L0_ContextLoadsTest,bdd.l1.RunL1CucumberTest" test

# L2 UI tests (requires frontend running on port 3000)
./mvnw -q -Dselenide.headless=true -Dtest="bdd.l2.RunL2CucumberTest" test
```

### Test Results Status
- ✅ **L0 Context:** PASSING
- ✅ **L1 Ping API:** PASSING
- ✅ **L1 Diagnostics API:** PASSING
- ❌ **L1 Fixtures API:** FAILING (500 error - known issue)
- ❌ **L2 UI Tests:** FAILING (requires frontend running)

## Why Windows Commands Are Different

The Maven wrapper (`mvnw`) behaves differently on Windows:
- **Unix/Linux/macOS:** Use `./mvnw` (shell script)
- **Windows:** Use `mvnw.cmd` (batch file)
- **PowerShell:** Requires `cmd /c mvnw.cmd` to execute batch files properly

This is why multiple command attempts were needed during testing.

## API Endpoints
- `GET /api/ping` → `{"status":"ok", "ts":"..."}`
- `GET /api/diag` → `{"mongo":false, "kafka":false, "external":false}`
- `POST /api/test/fixtures/seed` → Seed test data
- `GET /api/test/fixtures/list` → List test data
- `POST /api/test/fixtures/purge` → Clear test data

## Known Issues
1. **Fixtures endpoints return 500 error** - Bean configuration issue with in-memory repository
2. **L2 UI tests fail without frontend** - Expected behavior, start frontend first

## AWS Deployment
See `terraform/` directory and Wiki Deployment Guide for infrastructure setup.

## Troubleshooting
- **Maven wrapper not found:** Use `cmd /c mvnw.cmd` on Windows
- **Tests fail:** Ensure MongoDB is disabled with `-Dcodeforge.mongo.enabled=false`
- **Port conflicts:** Check ports 8080 (backend) and 3000 (frontend) are available

### Kill Process on Port 8080 (Windows)
```cmd
# Find process using port 8080
netstat -ano | findstr :8080

# Kill the process (replace PID with actual process ID)
taskkill /PID <PID> /F
```