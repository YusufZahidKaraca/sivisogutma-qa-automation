Feature: Invalid Coupon Code Validation at Checkout

  Scenario: Verifying that an invalid coupon code displays an error message
    Given g40. the user navigates to the product page to add item "https://sivisogutma.com/urun/coolmoon-argb-pwm-kontrolcu-siyah/"
    And g40. the user clicks the add to cart button
    When g40. the user navigates to the checkout or cart page "https://sivisogutma.com/cart/"
    And g40. the user enters an invalid coupon code "1234-5678-9999"
    And g40. the user clicks the apply coupon button
    Then g40. the user should see the coupon error message