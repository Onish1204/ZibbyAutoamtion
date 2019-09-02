package com.zibby.api.core;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Res {

    private static final String[] DEFAULT_RESOURCE_PATHS = {null, "src/main/resources", "src/test/resources"};

    public static URL getResource(String csvFile) {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(csvFile);

        if (resource == null) {
            for (String resourcePath : DEFAULT_RESOURCE_PATHS) {
                File resFile = new File(resourcePath, csvFile);
                if (resFile.isFile() || resFile.isDirectory()) {
                    try {
                        resource = resFile.toURI().toURL();
                        break;
                    } catch (MalformedURLException ex) {
                        throw new RuntimeException("Unable to locate the csv file");
                    }
                }
            }
        }
        return resource;
    }
}