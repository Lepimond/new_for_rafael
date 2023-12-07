package lepimond.commands;

import java.sql.SQLException;

import static lepimond.DBUtil.*;

public class AverageAgeCommand implements Command {
    @Override
    public void run() throws SQLException, NullPointerException {
        System.out.println(dao.getAvg("age"));
    }
}
