Feature: Multi Tab Session Management

  @Test30
  Scenario: 30 - Changing password in one tab after logging out in another
    Given C the user is logged into the "https://sivisogutma.com/hesabim" system
    When 30. the user opens a new tab and navigates to the same website
    And 30. the user logs out from the new tab
    And 30. the user switches back to the first tab
    And 30. the user attempts to change the password in the first tab
    Then 30. it is verified that the system requests a login or denies the action