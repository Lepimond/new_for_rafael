package lepimond;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class DBUtil {
    public static final String DB_NAME = "my_db";
    public static final String TABLE_NAME = "people";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/my_db";
    public static final String USER = "root";
    public static final String PASS = "root";
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
