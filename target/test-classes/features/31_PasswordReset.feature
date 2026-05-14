Feature: Password Reset Functionality

  Scenario: Successfully requesting a password reset link
    Given C the user navigates to the "https://sivisogutma.com/hesabim/lost-password/" website
    When 31. the user enters their registered email address "kemal@deneme123456.33mail.com"
    And 31. the user clicks the reset password button
    Then 31. it is verified that a password reset link sent message is displayed