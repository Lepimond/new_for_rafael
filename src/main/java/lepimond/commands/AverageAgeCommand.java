package lepimond.commands;

import lepimond.exceptions.PeopleCLIException;

import java.sql.SQLException;

import static lepimond.DBUtil.*;

public class AverageAgeCommand implements Command {
    @Override
    public void run() throws PeopleCLIException {
        System.out.println(dao.getAvg("age"));
    }
}
