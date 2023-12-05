package lepimond.commands;

import java.sql.SQLException;

import static lepimond.DBUtil.TABLE_NAME;
import static lepimond.DBUtil.stmt;

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
    public void run() throws SQLException {
        stmt.executeUpdate("INSERT INTO " + TABLE_NAME + " (first_name, last_name, age)\n" +
                "VALUES (\"" + first_name + "\", \"" + last_name + "\", " + age + ")");
    }
}
