package lepimond;

import lepimond.database_access.PersonDAO;

import java.util.Scanner;

public class DBUtil {
    public static String DB_NAME;
    public static String TABLE_NAME;
    public static String FILE_NAME;
    public static String DB_URL;
    public static String USER;
    public static String PASS;
    public static final Scanner scan = new Scanner(System.in);

    public static final PersonDAO dao = new PersonDAO();


}
