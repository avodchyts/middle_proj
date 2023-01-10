package utils;



import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class LocalFileReader {
    private static LocalFileReader localFileReader;
    private final static Logger LOGGER = Logger.getLogger(LocalFileReader.class);

    private LocalFileReader() {
    }

    public static LocalFileReader getLocalFileReaderInstance() {
        localFileReader = new LocalFileReader();
        return localFileReader;
    }
    public String getAppPropertiesValue(String propertiesValue) {
        return getPropertiesValue("src/main/java/resources/data.properties", propertiesValue).toString();
    }

    public  String getPropertiesValue(String fileName, String propertiesKey) {
        return getFilePropertiesValue(fileName).get(propertiesKey).toString();
    }

    public  Properties getFilePropertiesValue(String fileName) {
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
