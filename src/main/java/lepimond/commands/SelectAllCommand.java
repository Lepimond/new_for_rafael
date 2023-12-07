package lepimond.commands;

import lepimond.database_access.Person;

import java.sql.SQLException;
import java.util.ArrayList;

import static lepimond.DBUtil.*;

public class SelectAllCommand implements Command {

    @Override
    public void run() throws SQLException {
        ArrayList<Person> people = dao.getAll();

        System.out.printf("----------------------------------------------------%n");

        System.out.printf("|  id  |   first_name    |    last_name    |  age  |%n");
        System.out.printf("----------------------------------------------------%n");

        int id = 1;
        for (Person p: people) {
            System.out.printf("| %04d | %-15s | %-15s | %04d |%n",
                    id,
                    p.getFirstName(),
                    p.getLastName(),
                    p.getAge());
            System.out.printf("----------------------------------------------------%n");

            id++;
        }
    }
}
