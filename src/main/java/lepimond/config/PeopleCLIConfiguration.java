package lepimond.config;

import lepimond.exceptions.PeopleCLIException;
import lepimond.services.I18n;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import static lepimond.DBUtil.*;

public class PeopleCLIConfiguration {
    public static void readConfigs() throws PeopleCLIException {
        File configFile = new File("config.properties");

        Properties props = new Properties();
        try (FileReader reader = new FileReader(configFile)) {
            props.load(reader);
        } catch (IOException e) {
            throw new PeopleCLIException(I18n.getMessage("error_reading_config"), e);
        }

        FILE_NAME = props.getProperty("file_name");

        DB_NAME = props.getProperty("db_name");
        TABLE_NAME = props.getProperty("table_name");
        DB_URL = props.getProperty("db_url");
        USER = props.getProperty("user");
        PASS = props.getProperty("pass");
        LOGS = props.getProperty("logs");

        LOG_LEVEL = props.getProperty("log_level");
        LOGS_FILE_NAME = props.getProperty("logs_file_name");
    }
}
