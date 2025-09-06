
Feature: API L1 - ping, diag, fixtures

  Scenario: Ping returns ok
    Given the API base url is "http://localhost:8080"
    When I GET "/api/ping"
    Then the response code is 200
    And the json field "status" equals "ok"

  Scenario: Diagnostics returns flags
    Given the API base url is "http://localhost:8080"
    When I GET "/api/diag"
    Then the response code is 200
    And the json has fields "mongo","kafka","external"

  Scenario: Fixtures seed and list
    Given the API base url is "http://localhost:8080"
    When I POST "/api/test/fixtures/seed"
    And I GET "/api/test/fixtures/list"
    Then the response code is 200
