Feature: Change Password

  @Test29
  Scenario: 29 - Attempting to change the account password
    Given 29. the user logs in with a special account for password change at "https://sivisogutma.com/hesabim"
    When 29. the user goes to the account settings page
    And 29. the user enters the current password and a new password
    And 29. the user clicks the save changes button
    Then 29. it is verified that a password changed successfully message is displayed