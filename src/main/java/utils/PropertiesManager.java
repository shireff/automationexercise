package utils;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesManager {
    public static Properties webConfig;

    public PropertiesManager() {
    }

    public static void initializeProperties() {
        FileInputStream webConfigFile = null;
        try {
            webConfig = new Properties();
            webConfigFile = new FileInputStream("src/main/resources/WebConfigurations.properties");
            webConfig.load(webConfigFile);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }


    }


}