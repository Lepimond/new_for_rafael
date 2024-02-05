package lepimond.database_access;

import lepimond.exceptions.PeopleCLIException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static lepimond.DBUtil.*;
import static lepimond.PeopleCLI.executeQuery;
import static lepimond.PeopleCLI.executeUpdate;

public class PersonDAO implements DAO<Person> {

    @Override
    public Person get(int id) throws PeopleCLIException {
        try (ResultSet result = executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE id = " + id)) {
            result.next();

            return new Person(
                    result.getString(2),
                    result.getString(3),
                    result.getInt(4));
        } catch (SQLException e) {
            throw new PeopleCLIException("Ошибка в SQL-запросе", e);
        }
    }

    @Override
    public ArrayList<Person> getAll() throws PeopleCLIException {
        ArrayList<Person> resultingList = new ArrayList<>();

        try {
            ResultSet result = executeQuery("SELECT * FROM " + TABLE_NAME);

            while (result.next()) {
                resultingList.add(new Person(
                        result.getString(2),
                        result.getString(3),
                        result.getInt(4)));
            }

            return resultingList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new PeopleCLIException("Ошибка в SQL-запросе", e);
        }
    }

    @Override
    public double getAvg(String fieldName) throws PeopleCLIException {
        try (ResultSet result = executeQuery("SELECT AVG(" + fieldName + ") AS average_" + fieldName +
                " FROM " + TABLE_NAME)) {
            if (result.next()) {
                String str = result.getString("average_" + fieldName);
                return Double.parseDouble(str);
            } else {
                return -1.0;
            }
        } catch (SQLException e) {
            throw new PeopleCLIException("Ошибка в SQL-запросе", e);
        }
    }

    @Override
    public void save(Person person) throws PeopleCLIException {
        executeUpdate("INSERT INTO " + TABLE_NAME + " (first_name, last_name, age)\n" +
                "VALUES (\"" + person.getFirstName() + "\", \"" + person.getLastName() + "\", " + person.getAge() + ")");
    }

    @Override
    public void delete(int id) throws PeopleCLIException {
        executeUpdate("DELETE FROM " + TABLE_NAME + " WHERE id = " + id);
    }

    @Override
    public void deleteAll() throws PeopleCLIException {
        executeUpdate("TRUNCATE " + TABLE_NAME);
    }

    @Override
    public void update(int id, String updateContent) throws PeopleCLIException {
        executeUpdate("UPDATE " + TABLE_NAME + " SET " + updateContent + " WHERE id = " + id);
    }
}
