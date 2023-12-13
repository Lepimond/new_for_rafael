package lepimond.commands;

import lepimond.exceptions.PeopleCLIException;

import java.sql.SQLException;

import static lepimond.DBUtil.*;

public class DeleteCommand implements Command {

    private int id;

    public DeleteCommand(int id) {
        this.id = id;
    }

    @Override
    public void run() throws PeopleCLIException {
        dao.delete(id);
    }
}
