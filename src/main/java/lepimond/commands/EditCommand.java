package lepimond.commands;

import java.sql.SQLException;

import static lepimond.DBUtil.*;

public class EditCommand implements Command {

    private int id;
    private String edit;

    public EditCommand(int id, String edit) {
        this.id = id;
        this.edit = edit;
    }

    @Override
    public void run() throws SQLException {
        dao.update(id, edit);
    }
}
