package com.zibby.api.utils;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class XProperties {

    private static final Properties defaultProperties = getPropertiesFromFile("settings.properties");

    public static Properties getPropertiesFromFile(final String fileName) {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);

        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException ex) {
            throw new RuntimeException("Unable to load file: " + fileName);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
            }
        }
        return properties;
    }

    public static String getProperty(String key) {
        return defaultProperties.getProperty(key);
    }
}