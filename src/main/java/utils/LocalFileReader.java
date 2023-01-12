package utils;

import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public enum LocalFileReader {
    INSTANCE;
    private final static Logger LOGGER = Logger.getLogger(LocalFileReader.class);
    private static final String PROPS_FILE_NAME = "src/main/java/resources/data.properties";

    public String getAppPropertiesValue(String propertiesValue) {
        return getPropertiesValue(PROPS_FILE_NAME, propertiesValue).toString();
    }

    public String getPropertiesValue(String fileName, String propertiesKey) {
        return getFilePropertiesValue(fileName).get(propertiesKey).toString();
    }

    public Properties getFilePropertiesValue(String fileName) {
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
