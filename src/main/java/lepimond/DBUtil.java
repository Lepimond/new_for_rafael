package lepimond;

import com.mysql.jdbc.Driver;
import lepimond.database_access.PersonDAO;

import java.io.File;
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
    public static final Statement stmt;
    public static final Connection conn;

    static {
        try {
            readConfigs();

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    ;
    public static final Scanner scan = new Scanner(System.in);

    public static final PersonDAO dao = new PersonDAO();

    public static boolean databaseExists(String databaseName) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet resultSet = meta.getCatalogs();

        String currentName;
        do {
            if (!resultSet.next()) {
                return false;
            }

            currentName = resultSet.getString(1);
        } while(!currentName.equals(databaseName));

        return true;
    }

    public static boolean tableExists(String tableName) throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[]{"TABLE"});
        return resultSet.next();
    }

    public static void readConfigs() throws IOException {
        File configFile = new File("config.properties");

        Properties props = new Properties();
        try (FileReader reader = new FileReader(configFile)) {
            props.load(reader);
        }

        FILE_NAME = props.getProperty("file_name");

        DB_NAME = props.getProperty("db_name");
        TABLE_NAME = props.getProperty("table_name");
        DB_URL = props.getProperty("db_url");
        USER = props.getProperty("user");
        PASS = props.getProperty("pass");
    }
}
