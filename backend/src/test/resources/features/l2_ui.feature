
Feature: UI L2 - FE↔BE Ping

  Scenario: Frontend displays ping result
    Given the FE url is "http://localhost:3000"
    When I open the home page
    And I click the "Ping API" button
    Then I should see text "status: ok"
