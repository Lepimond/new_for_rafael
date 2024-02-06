package lepimond;

import lepimond.database_access.PersonDAO;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class DBUtil {
    public static String DB_NAME;
    public static String TABLE_NAME;
    public static String FILE_NAME;
    public static String DB_URL;
    public static String USER;
    public static String PASS;
    public static String LOGS;
    public static String LOG_LEVEL;
    public static String CONSOLE_LOG;
    public static String FILE_LOG;
    public static final Scanner scan = new Scanner(System.in);

    public static final PersonDAO dao = new PersonDAO();
    public static Logger logger = org.apache.log4j.Logger.getLogger(DBUtil.class);
    public static void closeScanner() {
        scan.close();
    }
}
