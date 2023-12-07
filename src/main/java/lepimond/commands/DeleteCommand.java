package lepimond.commands;

import java.sql.SQLException;

import static lepimond.DBUtil.*;

public class DeleteCommand implements Command {

    private int id;

    public DeleteCommand(int id) {
        this.id = id;
    }

    @Override
    public void run() throws SQLException {
        dao.delete(id);
    }
}
