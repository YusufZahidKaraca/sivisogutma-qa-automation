@scenario13
Feature: Stock Persistence and Variable Quantity Operations

  Background: Tarayıcıyı aç ve ana sayfaya git
    Given scenario13 I navigate to "https://sivisogutma.com"

  Scenario: Karmaşık miktar ve çoklu ürün stres testi
    # 1. Ürün: 10 Adet
    When scenario13 I search for product code "Alphacool Core 120mm fan PWM 4000rpm"
    And scenario13 I add "10" items to the cart

    # 2. Ürün: 1 Adet
    And scenario13 I search for product code "EK-Classic CPU Water Block 115x20xx D-RGB"
    And scenario13 I click the add to cart button

    # 3. Ürün: 1 Adet
    And scenario13 I search for product code "Alphacool Core Geforce RTX 4090 Strix + TUF ve Backplate"
    And scenario13 I click the add to cart button

    # 4. Ürün: 10 Adet
    And scenario13 I search for product code "Thermaltake V-Tubler PETG Tüpü 5/8”(16mm) OD 500mm"
    And scenario13 I add "10" items to the cart

    # 5. Ürün: 2 Adet
    And scenario13 I search for product code "CORSAIR Hydro X Series XD5 RGB Pump/Reservoir Combo"
    And scenario13 I add "2" items to the cart

    # 6. Ürün: 1 Adet
    And scenario13 I search for product code "RAIJINTEK CALORE ELITE CA240 Bakır Radyatör"
    And scenario13 I click the add to cart button

    # 7. Ürün: 15 Adet
    And scenario13 I search for product code "EK-Quantum Torque Extender Static MF 7"
    And scenario13 I add "15" items to the cart

    # 8. Ürün: 8 Adet
    And scenario13 I search for product code "EK-Quantum Torque Rotary 45° Black Nickel"
    And scenario13 I add "8" items to the cart

    # 9. Ürün: 13 Adet
    And scenario13 I search for product code "EK-Quantum Torque Static FF 90° Nickel"
    And scenario13 I add "13" items to the cart

    # 10. Ürün: 3 Adet
    And scenario13 I search for product code "Corsair Hydro X Serisi XR7 240mm Radyatör"
    And scenario13 I add "3" items to the cart

    # Final: Sepete git
    And scenario13 I go to the cart page and click plus button for the first item
    Then scenario13 The product should be added to the cart successfully