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
        final Person[] person = {null};

        executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE id = " + id, result -> {


            try (ResultSet resultSet = (ResultSet) result) {
                resultSet.next();

                person[0] = new Person(
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getInt(4));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        });
        return person[0];
    }

    @Override
    public ArrayList<Person> getAll() throws PeopleCLIException {
        ArrayList<Person> resultingList = new ArrayList<>();

        executeQuery("SELECT * FROM " + TABLE_NAME, result -> {


            try (ResultSet resultSet = (ResultSet) result) {
                while (resultSet.next()) {
                    resultingList.add(new Person(
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4)));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException("Ошибка в SQL-запросе", e);
            }


        });

        return resultingList;
    }

    @Override
    public double getAvg(String fieldName) throws PeopleCLIException {
        final Double[] avg = {null};

        executeQuery("SELECT AVG(" + fieldName + ") AS average_" + fieldName +
                " FROM " + TABLE_NAME, result -> {


            try (ResultSet resultSet = (ResultSet) result) {
                if (resultSet.next()) {
                    String str = resultSet.getString("average_" + fieldName);
                    avg[0] = Double.parseDouble(str);
                } else {
                    avg[0] = -1.0;
                }
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка в SQL-запросе", e);
            }
        });

        return avg[0];
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
