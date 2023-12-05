package lepimond.commands;

import java.sql.ResultSet;
import java.sql.SQLException;

import static lepimond.DBUtil.*;

public class AverageAgeCommand implements Command {
    @Override
    public void run() throws SQLException, NullPointerException {
        ResultSet result = stmt.executeQuery("SELECT AVG(age) AS average_age FROM " + TABLE_NAME);
        if (result.next()) {
            String str = result.getString("average_age");
            System.out.println(Double.parseDouble(str));
        } else {
            System.out.println(-1.0);
        }
    }
}
