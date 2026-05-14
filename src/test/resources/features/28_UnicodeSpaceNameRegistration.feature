Feature: Unicode Space Name Registration

  @Test28
  Scenario: 28 - Attempting to register with a unicode space as the name
    Given C the user navigates to the "https://sivisogutma.com/hesabim" website
    And 28. the user enters a unicode space in the name field and valid data in other fields
    And C the user clicks the register button
    Then 28. it is verified that an invalid name or empty field error message is displayed