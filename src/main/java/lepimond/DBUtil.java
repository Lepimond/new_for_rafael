package lepimond;

import lepimond.database_access.PersonDAO;
import lepimond.exceptions.PeopleCLIException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class DBUtil {
    public static String DB_NAME;
    public static String TABLE_NAME;
    public static String FILE_NAME;
    public static String DB_URL;
    public static String USER;
    public static String PASS;
    public static Statement stmt;
    public static Connection conn;
    public static final Scanner scan = new Scanner(System.in);

    public static final PersonDAO dao = new PersonDAO();

    public static boolean databaseExists(String databaseName) throws PeopleCLIException {
        DatabaseMetaData meta;
        try {
            meta = conn.getMetaData();
        } catch (SQLException e) {
            throw new PeopleCLIException("Ошибка при проверке существования базы данных", e);
        }
        try (ResultSet resultSet = meta.getCatalogs()) {
            String currentName;
            do {
                if (!resultSet.next()) {
                    return false;
                }

                currentName = resultSet.getString(1);
            } while(!currentName.equals(databaseName));

            return true;
        } catch (SQLException e) {
            throw new PeopleCLIException("Ошибка при проверке существования базы данных", e);
        }
    }

    public static boolean tableExists(String tableName) throws PeopleCLIException {
        DatabaseMetaData meta;
        try {
            meta = conn.getMetaData();
        } catch (SQLException e) {
            throw new PeopleCLIException("Ошибка при проверке существования таблицы", e);
        }
        try (ResultSet resultSet = meta.getTables(null, null, tableName, new String[]{"TABLE"})) {
            return resultSet.next();
        } catch (SQLException e) {
            throw new PeopleCLIException("Ошибка при проверке существования таблицы", e);
        }
    }

    public static void readConfigs() throws PeopleCLIException {
        File configFile = new File("config.properties");

        Properties props = new Properties();
        try (FileReader reader = new FileReader(configFile)) {
            props.load(reader);
        } catch (IOException e) {
            throw new PeopleCLIException("Ошибка при чтении файлов конфигурации", e);
        }

        FILE_NAME = props.getProperty("file_name");

        DB_NAME = props.getProperty("db_name");
        TABLE_NAME = props.getProperty("table_name");
        DB_URL = props.getProperty("db_url");
        USER = props.getProperty("user");
        PASS = props.getProperty("pass");
    }
}
