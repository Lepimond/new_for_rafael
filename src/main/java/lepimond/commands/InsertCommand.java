package lepimond.commands;

import lepimond.database_access.Person;
import lepimond.exceptions.PeopleCLIException;

import static lepimond.DBUtil.*;

public class InsertCommand implements Command {

    private String first_name;
    private String last_name;
    private int age;

    public InsertCommand(String first_name, String last_name, int age) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
    }

    @Override
    public void run() throws PeopleCLIException {
        dao.save(new Person(first_name, last_name, age));
    }
}
