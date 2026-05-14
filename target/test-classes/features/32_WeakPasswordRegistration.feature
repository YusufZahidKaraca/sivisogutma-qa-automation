Feature: Weak Password Registration

  @Test32
  Scenario: 32 - Attempting to register with a weak password
    Given C the user navigates to the "https://sivisogutma.com/hesabim" website
    And 32. the user enters a valid email but a weak password
    Then 32. it is verified that a weak password error message is displayed