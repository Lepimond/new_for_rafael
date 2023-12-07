package lepimond.database_access;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static lepimond.DBUtil.*;

public class PersonDAO implements DAO<Person> {
    @Override
    public Person get(int id) throws SQLException {
        try (ResultSet result = stmt.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE id = " + id)) {
            result.next();

            return new Person(
                    result.getString(2),
                    result.getString(3),
                    result.getInt(4));
        }
    }

    @Override
    public ArrayList<Person> getAll() throws SQLException {
        ArrayList<Person> resultingList = new ArrayList<>();

        try (ResultSet result = stmt.executeQuery("SELECT * FROM " + TABLE_NAME)) {
            while (result.next()) {
                resultingList.add(new Person(
                        result.getString(2),
                        result.getString(3),
                        result.getInt(4)));
            }

            return resultingList;
        }
    }

    @Override
    public double getAvg(String fieldName) throws SQLException {
        try (ResultSet result = stmt.executeQuery("SELECT AVG(" + fieldName + ") AS average_" + fieldName +
                " FROM " + TABLE_NAME)) {
            if (result.next()) {
                String str = result.getString("average_" + fieldName);
                return Double.parseDouble(str);
            } else {
                return -1.0;
            }
        }
    }

    @Override
    public void save(Person person) throws SQLException {
        stmt.executeUpdate("INSERT INTO " + TABLE_NAME + " (first_name, last_name, age)\n" +
                "VALUES (\"" + person.getFirstName() + "\", \"" + person.getLastName() + "\", " + person.getAge() + ")");
    }

    @Override
    public void delete(int id) throws SQLException {
        stmt.executeUpdate("DELETE FROM " + TABLE_NAME + " WHERE id = " + id);
    }

    @Override
    public void deleteAll() throws SQLException {
        stmt.executeUpdate("TRUNCATE " + TABLE_NAME);
    }

    @Override
    public void update(int id, String updateContent) throws SQLException {
        stmt.executeUpdate("UPDATE " + TABLE_NAME + " SET " + updateContent + " WHERE id = " + id);
    }
}
