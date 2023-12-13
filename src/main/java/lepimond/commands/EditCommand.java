package lepimond.commands;

import lepimond.exceptions.PeopleCLIException;

import static lepimond.DBUtil.*;

public class EditCommand implements Command {

    private int id;
    private String edit;

    public EditCommand(int id, String edit) {
        this.id = id;
        this.edit = edit;
    }

    @Override
    public void run() throws PeopleCLIException {
        dao.update(id, edit);
    }
}
