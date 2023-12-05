package lepimond.commands;

import java.sql.SQLException;

import static lepimond.DBUtil.TABLE_NAME;
import static lepimond.DBUtil.stmt;

public class DeleteAllCommand implements Command {
    @Override
    public void run() throws SQLException {
        stmt.executeUpdate("TRUNCATE " + TABLE_NAME);
    }
}