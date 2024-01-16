package lepimond.commands;

import lepimond.database_access.Person;
import lepimond.exceptions.PeopleCLIException;

import java.util.ArrayList;

import static lepimond.DBUtil.*;

public class SelectAllCommand implements Command {

    @Override
    public void run() throws PeopleCLIException {
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
