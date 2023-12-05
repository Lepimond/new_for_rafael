package lepimond.commands;

import java.sql.SQLException;

import static lepimond.DBUtil.TABLE_NAME;
import static lepimond.DBUtil.stmt;

public class EditCommand implements Command {

    private int id;
    private String edit;

    public EditCommand(int id, String edit) {
        this.id = id;
        this.edit = edit;
    }

    @Override
    public void run() throws SQLException {
        stmt.executeUpdate("UPDATE " + TABLE_NAME + " SET " + edit + " WHERE id = " + id);
    }
}
