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
}
