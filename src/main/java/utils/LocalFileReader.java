package utils;



import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class LocalFileReader {
    private final static Logger LOGGER = Logger.getLogger(LocalFileReader.class);

    public LocalFileReader() {
    }

    public static String getAppPropertiesValue(String propertiesValue) {
        return getPropertiesValue("src/main/java/resources/data.properties", propertiesValue).toString();
    }

    public static String getPropertiesValue(String fileName, String propertiesKey) {
        return getFilePropertiesValue(fileName).get(propertiesKey).toString();
    }

    public static Properties getFilePropertiesValue(String fileName) {
        Properties properties = new Properties();
        try {
            FileReader fileReader = new FileReader(fileName);
            properties.load(fileReader);
        } catch (FileNotFoundException exception) {
            LOGGER.error(exception);
        } catch (IOException e) {
            LOGGER.error(e);
        }
        return properties;
    }
}
