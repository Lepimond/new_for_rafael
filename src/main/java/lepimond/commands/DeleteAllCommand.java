package lepimond.commands;

import lepimond.exceptions.PeopleCLIException;

import static lepimond.DBUtil.*;

public class DeleteAllCommand implements Command {
    @Override
    public void run() throws PeopleCLIException {
        dao.deleteAll();
    }
}