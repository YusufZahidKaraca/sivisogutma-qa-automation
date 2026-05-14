@47.
Feature: Checkout Form Persistence and Cart Integration

  Background: Navigate to the home page
    Given 47. I navigate to "https://sivisogutma.com"

  Scenario: Verify that checkout data behavior after page refresh
    # 1. Aşama: Ürünü Sepete Hazırla (Ödeme sayfasına giriş izni için)
    When 47. I search for product code "Alphacool Core 120mm fan PWM 4000rpm"
    And 47. I add all available items to the cart

    # 2. Aşama: Ödeme Sayfasına Geç ve Formu Doldur
    And 47. I go to the cart page and try to exceed stock limit

    # 3. Aşama: Form Doldurma, F5 ve Doğrulama
    Then 47. the checkout form fields should be filled and refreshed