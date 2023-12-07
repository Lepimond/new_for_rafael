package lepimond.commands;

import lepimond.database_access.Person;

import java.sql.SQLException;

import static lepimond.DBUtil.*;

public class SelectCommand implements Command {

    private int id;

    public SelectCommand(int id) {
        this.id = id;
    }

    @Override
    public void run() throws SQLException {
        Person person = dao.get(id);

        System.out.printf("----------------------------------------------------%n");

        System.out.printf("|  id  |   first_name    |    last_name    |  age  |%n");
        System.out.printf("----------------------------------------------------%n");

        System.out.printf("| %04d | %-15s | %-15s | %04d |%n",
                id,
                person.getFirstName(),
                person.getLastName(),
                person.getAge());
        System.out.printf("----------------------------------------------------%n");
    }
}
