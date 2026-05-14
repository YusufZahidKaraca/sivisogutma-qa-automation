Feature: Fill Favorites Page with Multiple Products

  Scenario: Adding 53 products to favorites and verifying success
    Given g22. the user adds the following products to favorites
      | https://sivisogutma.com/urun/corsair-hydro-x-series-xt-pressure-leak-tester-tool-kit/ |
      | https://sivisogutma.com/urun/ek-loop-soft-tube-cutter-esnek-hortum-kesme-aparati/ |
      | https://sivisogutma.com/urun/m_m-psu-sokme-aparatlari-4lu-takim/ |
      | https://sivisogutma.com/urun/m_m-atx-molex-pin-takma-aleti/ |
      | https://sivisogutma.com/urun/cavexpc-rahat-sivi-dolum-sisesi-1000ml/ |
      | https://sivisogutma.com/urun/cavexpc-rahat-sivi-dolum-sisesi-500ml/ |
      | https://sivisogutma.com/urun/ek-allen-key-25mm/ |
      | https://sivisogutma.com/urun/ek-hd-tube-d-i-y-kit-1012mm/ |
      | https://sivisogutma.com/urun/thermaltake-12mm-slicon-43cm-gray-id12-od16mm/ |
      | https://sivisogutma.com/urun/thernaltake-psu-kopru-aleti-24-pin-psu-calistirma-soket-siyah/ |
      | https://sivisogutma.com/urun/xspc-atx-psu-kopru-aleti-24-pin-siyah/ |
      | https://sivisogutma.com/urun/coolmoon-argb-pwm-kontrolcu-beyaz/ |
      | https://sivisogutma.com/urun/coolmoon-argb-pwm-kontrolcu-siyah/ |
      | https://sivisogutma.com/urun/alphacool-core-120mm-fan-pwm-3000rpm-120x120x25mm/ |
      | https://sivisogutma.com/urun/alphacool-core-120mm-fan-pwm-4000rpm-120x120x25mm/ |
      | https://sivisogutma.com/urun/alphacool-core-140mm-fan-pwm-3200rpm-140x140x25mm/ |
      | https://sivisogutma.com/urun/alphacool-ice-cyclone-aurora-lux-pro-2-digital-rgb/ |
      | https://sivisogutma.com/urun/ek-d-rgb-6-way-splitter-kablo/ |
      | https://sivisogutma.com/urun/m-m-rog-10-port-pwm-hub-fan-coklayici/ |
      | https://sivisogutma.com/urun/mm-corsair-4-pin-argb-adapter/ |
      | https://sivisogutma.com/urun/ek-rgb-4-way-splitter-cable/ |
      | https://sivisogutma.com/urun/mm_88-pin-islemci-cogaltma-kablosu/ |
      | https://sivisogutma.com/urun/mm-yumusak-fan-filtresi-500x400x3mm/ |
      | https://sivisogutma.com/urun/thermaltake-pure-12-argb-sync-radyator-fan-tt-premium-edition-3lu-paket-2/ |
      | https://sivisogutma.com/urun/cavexpc-carbon-3k-boru14x12x1000mm-mat-siyah/ |
      | https://sivisogutma.com/urun/alphacool-hardtube-13mm-od-brass-deep-black-40cm/ |
      | https://sivisogutma.com/urun/corsair-hydro-x-serisi-xt-hardline-1000mm-12mm-boru-satin-transparent/ |
      | https://sivisogutma.com/urun/corsair-hydro-x-serisi-xt-hardline-1000mm-14mm-boru-saten-siyah/ |
      | https://sivisogutma.com/urun/alphacool-eiszapfen-g1-4-stop-rakoru-deep-black/ |
      | https://sivisogutma.com/urun/ek-hd-petg-insert-12-16mm-10pcs/ |
      | https://sivisogutma.com/urun/monsoon-hardline-acrylic-tubing-13-16mm-61cm-boru-uv-red/ |
      | https://sivisogutma.com/urun/thermaltake-v-tubler-petg-tupu-5-816mm-od-500mm-4lu-paket/ |
      | https://sivisogutma.com/urun/alphacool-eiszapfen-double-nippel-rotaryli-g1-4-outer-thread-to-g1-4-outer-thread-deep-black/ |
      | https://sivisogutma.com/urun/alphacool-eiszapfen-double-nippel-rotaryli-g1-4-outer-thread-to-g1-4-outer-thread-krom/ |
      | https://sivisogutma.com/urun/m_m-pwm-coklayici-hub-1-to-5/ |
      | https://sivisogutma.com/urun/m_m-12vhpwr-gen5-rtx4000-5000-serisi-sleeve-kablo/ |
      | https://sivisogutma.com/urun/bitspower-g14-matt-black-premium-quick-13-19mm-hortum-rakoru/ |
      | https://sivisogutma.com/urun/corsair-hydro-x-series-xd5-rgb-pump-reservoir-combo-white/ |
      | https://sivisogutma.com/urun/ek-ddc-heatsink-housing-pompa-sogutucu-ve-sessizlestirici-kit-siyah/ |
      | https://sivisogutma.com/urun/ek-o-ring-for-ddc-tops-ddc-pompalar-icin-o-ring/ |
      | https://sivisogutma.com/urun/thermaltake-pacific-pr15-ddc-reservoir-pump-combo-kit-2/ |
      | https://sivisogutma.com/urun/corsair-hydro-x-serisi-xr7-240mm-radyator/ |
      | https://sivisogutma.com/urun/ek-uni-rad-holder-140mm-montaj-aparati/ |
      | https://sivisogutma.com/urun/ek-quantum-torque-extender-static-mf-7-black/ |
      | https://sivisogutma.com/urun/ek-quantum-torque-static-ff-90-nickel/ |
      | https://sivisogutma.com/urun/alphacool-eiszapfen-quick-release-coupling-set-g1-4-ig-deep-black/ |
      | https://sivisogutma.com/urun/barrow-t-kup-4-way-rotary-siyah/ |
      | https://sivisogutma.com/urun/corsair-hydro-x-serisi-xf-hardline-rakoru-12mm-dortlu-paket-siyah/ |
      | https://sivisogutma.com/urun/ek-af-y-splitter-3f-g1-4-siyah/ |
      | https://sivisogutma.com/urun/ek-af-y-splitter-rotary-2f-1m-g1-4-nikel/ |
      | https://sivisogutma.com/urun/ek-htc-classic-12mm-sert-boru-rakoru-nikel/ |
      | https://sivisogutma.com/urun/ek-plug-g1-4-acetal-stop-rakoru-black-10lu-paket/ |
      | https://sivisogutma.com/urun/alphacool-eiszapfen-pressure-valve-g1-4-krom/ |
    When g22. the user navigates to the favorites page after adding all items
    Then g22. the user should see the specific target element on the favorites page