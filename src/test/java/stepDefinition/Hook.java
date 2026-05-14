package stepDefinition;

import io.cucumber.java.After;
import utilities.Driver;

public class Hook {

    // Cucumber, senaryo Pass da olsa Fail de olsa bu metodu otomatik olarak her testin EN SONUNDA çalıştırır.
    @After
    public void tearDown() {
        try {
            Thread.sleep(1500); // Tarayıcı kapanmadan önce son durumu ekranda görmek için kısa mola
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Driver sınıfındaki güvenli kapatma metodunu çağırıyoruz.
        // (Bu metot tarayıcıyı kapatıp içini sıfırlayacak, böylece sonraki test yepyeni bir tarayıcı açacak).
        Driver.closeDriver();
    }
}