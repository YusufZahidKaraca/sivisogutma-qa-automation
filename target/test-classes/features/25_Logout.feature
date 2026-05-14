Feature: User Logout

  @Test25
  Scenario: 25 - Logging out of the account
    Given C the user is logged into the "https://sivisogutma.com/hesabim" system
    When 25. the user clicks the logout button from the account menu
    Then 25. it is verified that the user is successfully logged out