package lepimond.commands;

import java.sql.SQLException;

import static lepimond.DBUtil.*;

public class DeleteAllCommand implements Command {
    @Override
    public void run() throws SQLException {
        dao.deleteAll();
    }
}