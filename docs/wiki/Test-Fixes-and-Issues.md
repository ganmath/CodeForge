# Test Fixes and Issues

## Overview
This document tracks the fixes applied to resolve test issues and the current status of the test suite.

## Issues Fixed

### 1. Bean Conflict in FixturesController
**Problem**: `FixturesController` had a dependency injection conflict between `inMemoryTenantRepository` and `tenantRepository` beans.

**Solution**: 
- Added `@Primary` annotation to `inMemoryTenantRepository` bean in `RepoConfig.java`
- Removed `@Qualifier` annotation from `FixturesController` constructor
- Added `@EnableAutoConfiguration(exclude = {...})` to test base class to prevent MongoDB auto-configuration

**Files Modified**:
- `src/main/java/com/codeforge/config/RepoConfig.java`
- `src/main/java/com/codeforge/api/FixturesController.java`
- `src/test/java/bdd/l1/L1IntegrationTestBase.java`

### 2. Cucumber Test Configuration
**Problem**: Cucumber tests couldn't find feature files and step definitions.

**Solution**:
- Created `junit-platform.properties` to configure Cucumber features and glue locations
- Fixed step definitions package structure (`bdd.l1.steps` vs `bdd.l1`)
- Removed duplicate step definitions file

**Files Created/Modified**:
- `src/test/resources/junit-platform.properties`
- `src/test/resources/application-test.yml`
- Removed duplicate `src/test/java/bdd/l1/ApiSteps.java`

### 3. Integration Test Configuration
**Problem**: API tests needed web server to be running during test execution.

**Solution**:
- Created `L1IntegrationTestBase` class with `@SpringBootTest(webEnvironment = DEFINED_PORT)`
- Made `RunL1CucumberTest` extend the integration test base

**Files Created**:
- `src/test/java/bdd/l1/L1IntegrationTestBase.java`

## Current Test Status

### ✅ L0 Context Test
- **Status**: PASSING
- **Description**: Spring context loads successfully with MongoDB disabled

### ✅ L1 API Tests (Partial)
- **Ping Test**: PASSING - `/api/ping` returns `{"status":"ok"}`
- **Diagnostics Test**: PASSING - `/api/diag` returns connectivity flags
- **Fixtures Test**: ❌ FAILING - Returns 500 error (needs investigation)

### ❌ L2 UI Tests
- **Status**: FAILING
- **Reason**: Frontend not running on port 3000 during test execution
- **Note**: Expected behavior for backend-only test run

## Running Tests

### L0 + L1 Tests
```bash
cd backend
mvn -q -Dcodeforge.mongo.enabled=false -Dtest="com.codeforge.L0_ContextLoadsTest,bdd.l1.RunL1CucumberTest" test
```

### L0 Only
```bash
cd backend
mvn -q -Dcodeforge.mongo.enabled=false -Dtest="com.codeforge.L0_ContextLoadsTest" test
```

## Outstanding Issues

### Fixtures Endpoint 500 Error
The `/api/test/fixtures/seed` and `/api/test/fixtures/list` endpoints are returning 500 errors even with the in-memory repository configuration. This needs further investigation to determine if:
- There's still a bean configuration issue
- The in-memory repository implementation has a bug
- The test scenario expectations are incorrect

### L2 UI Test Dependencies
L2 tests require the frontend to be running on port 3000. For full integration testing, the frontend should be started before running L2 tests.

## Next Steps
1. Debug the fixtures endpoint 500 error
2. Consider separating L1 and L2 test execution
3. Add test profiles for different test levels
4. Investigate frontend startup automation for L2 tests