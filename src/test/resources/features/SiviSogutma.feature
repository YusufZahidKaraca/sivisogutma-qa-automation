@regression
Feature: Search and Cart Functionality Tests for SiviSogutma

  Background:
    Given user navigates to the homepage

  # --- SEARCH FUNCTIONALITY ---

  @search @smoke
  Scenario: 1- Valid product search
    When user searches for a valid product "sıvı soğutma"
    Then user should see relevant search results

  @search
  Scenario: 2- Invalid product search
    When user searches for an invalid product "xyz123invalid"
    Then user should see a no results found message

  @search @boundary_test
  Scenario: 3- Attempt to fill search bar with 999 characters
    When user inputs a string of 999 characters into the search bar
    And clicks the search button
    Then the system should handle the input without crashing

  @search
  Scenario: 4- Search with unicode space
    When user searches using a unicode space "\u00A0"
    Then the system should process the unicode search appropriately

  @search
  Scenario: 5- Search with normal space
    When user searches using a normal space " "
    Then user should remain on the current page or see default results

  @search
  Scenario: 6- Empty search within a selected category
    When user selects a category
    And performs an empty search
    Then the system should display all products in that category

  # --- CART AND ADD TO CART FUNCTIONALITY ---

  @cart
  Scenario: 7- Go to cart when empty
    When user navigates to the cart page directly
    Then user should see an empty cart message

  @cart @smoke
  Scenario: 8- Add product to cart
    When user clicks add to cart for a product
    Then the product should be added to the cart successfully

  @cart
  Scenario: 9- Add product again when already in cart
    When user clicks add to cart for a product
    And user clicks add to cart for the same product again
    Then the product quantity should be updated in the cart

  @cart
  Scenario: 10- Decrease product quantity in cart
    Given user has a product in the cart with quantity 2
    When user decreases the product quantity by 1
    Then the cart should display the product with quantity 1

  @cart
  Scenario: 11- Delete product from cart directly with trash icon
    Given user has a product in the cart
    When user clicks the trash icon for the product
    Then the product should be removed from the cart

  @cart @negative_test
  Scenario: 12- Attempt to add negative quantity to cart
    When user attempts to input "-1" as product quantity
    And clicks add to cart
    Then the system should prevent adding negative quantities