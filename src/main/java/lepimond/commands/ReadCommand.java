package lepimond.commands;

import lepimond.DBUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

import static lepimond.DBUtil.*;

public class ReadCommand implements Command {
    @Override
    public void run() throws IOException, SQLException {
        makeTable(readFile(FILE_NAME));
    }

    private String readFile(String path) throws IOException {
        Path fileName = Path.of(path);
        return Files.readString(fileName);
    }

    private void makeTable(String jsonInput) throws SQLException {
        if (DBUtil.tableExists(TABLE_NAME)) {
            stmt.executeUpdate("DROP TABLE " + TABLE_NAME);
        }

        stmt.executeUpdate("""
                       CREATE TABLE people (
                       id int AUTO_INCREMENT,
                       first_name varchar(255),
                       last_name varchar(255),
                       age int,
                       PRIMARY KEY(id)
                       );""");
        JSONArray json = new JSONArray(jsonInput);

        for(int i = 0; i < json.length(); ++i) {
            insertLine(json, i);
        }
    }

    private void insertLine(JSONArray json, int lineNumber) throws SQLException {
        JSONObject currentObject = json.getJSONObject(lineNumber);
        String firstName = currentObject.getString("first_name");
        String lastName = currentObject.getString("last_name");
        int age = currentObject.getInt("age");
        this.insert(firstName, lastName, age);
    }

    private void insert(String first_name, String last_name, int age) throws SQLException {
        stmt.executeUpdate("INSERT INTO " + TABLE_NAME + " (first_name, last_name, age)\n" +
                "VALUES (\"" + first_name + "\", \"" + last_name + "\", " + age + ")");
    }
}
