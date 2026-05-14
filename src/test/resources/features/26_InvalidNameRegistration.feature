Feature: Invalid Name Registration

  @Test26
  Scenario: 26 - Attempting to register with numbers instead of a name
    Given C the user navigates to the "https://sivisogutma.com/hesabim" website
    And 26. the user enters numbers in the name field and valid data in other fields
    And C the user clicks the register button
    Then 26. it is verified that an invalid name error message is displayed