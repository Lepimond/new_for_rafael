package lepimond;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DBUtil {
    public static String DB_NAME;
    public static String TABLE_NAME;
    public static String FILE_NAME = "";
    public static String DB_URL;
    public static String USER;
    public static String PASS;
    public static Statement stmt;
    public static Connection conn;
    public static final Scanner scan = new Scanner(System.in);

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
}
