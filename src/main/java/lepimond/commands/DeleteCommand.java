package lepimond.commands;

import java.sql.SQLException;

import static lepimond.DBUtil.TABLE_NAME;
import static lepimond.DBUtil.stmt;

public class DeleteCommand implements Command {

    private int id;

    public DeleteCommand(int id) {
        this.id = id;
    }

    @Override
    public void run() throws SQLException {
        stmt.executeUpdate("DELETE FROM " + TABLE_NAME + " WHERE id = " + id);
    }
}
