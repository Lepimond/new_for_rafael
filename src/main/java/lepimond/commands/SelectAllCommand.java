package lepimond.commands;

import java.sql.ResultSet;
import java.sql.SQLException;

import static lepimond.DBUtil.TABLE_NAME;
import static lepimond.DBUtil.stmt;

public class SelectAllCommand implements Command {

    @Override
    public void run() throws SQLException {
        ResultSet result = stmt.executeQuery("SELECT * FROM " + TABLE_NAME);

        System.out.printf("----------------------------------------------------%n");

        System.out.printf("|  id  |   first_name    |    last_name    |  age  |%n");
        System.out.printf("----------------------------------------------------%n");

        while(result.next()) {
            System.out.printf("| %04d | %-15s | %-15s | %04d |%n",
                    result.getInt(1),
                    result.getString(2),
                    result.getString(3),
                    result.getInt(4));
            System.out.printf("----------------------------------------------------%n");
        }
    }
}
