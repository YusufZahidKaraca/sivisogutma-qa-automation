@23.
Feature: Registration with Temporary Mail

  Scenario: Try to create an account using a temporary email service
    Given 23. I navigate to a temporary mail service to get an email
    And 23. I navigate to "https://sivisogutma.com/hesabim/"
    When 23. I fill the registration form with temporary email and a password
    Then 23. The registration should be blocked for temporary email