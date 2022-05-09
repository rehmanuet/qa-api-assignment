package utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;

import java.util.Properties;

@Slf4j
public class FileUtils {


    /**
     * Get the values of the properties from the properties
     *
     * @param key, Key of the property to get value for
     * @return propertyValue
     */
    public static synchronized String getPropertyValue(String filePath, String key) {

        String propertyValue = "message not found - ";
        try (InputStream input = new FileInputStream(filePath)) {
            Properties prop = new Properties();
            // load a properties file
            prop.load(input);

            // get the property value and print it out
            propertyValue = prop.getProperty(key);
        }
        //In case the message isn't retrieved, the test case flow can continue,
        //as this is just for console logging, and the exceptions will be logged in the console.
        catch (IOException ex) {
            log.error(ex.getMessage());
        }
        return propertyValue;
    }
}
