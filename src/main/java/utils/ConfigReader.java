package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try {
            // Dosyanın yolunu belirtiyoruz
            String path = "configuration.properties";
            FileInputStream input = new FileInputStream(path);
            properties = new Properties();
            properties.load(input);
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("configuration.properties dosyasi bulunamadi!");
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}