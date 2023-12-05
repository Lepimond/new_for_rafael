package lepimond;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;

import lepimond.commands.*;
import org.json.JSONArray;
import org.json.JSONObject;

import static lepimond.DBUtil.*;

public enum CommandType {
    READ, DELETE, DELETE_ALL, INSERT, EDIT, AVG_AGE, SELECT_ALL, SELECT, HELP;

    public void run() throws SQLException, IOException {
        switch (this) {
            case READ -> makeTable(readFile(FILE_NAME));
            case DELETE -> new DeleteCommand(scan.nextInt()).run();
            case DELETE_ALL -> new DeleteAllCommand().run();
            case INSERT -> new InsertCommand(scan.next(), scan.next(), scan.nextInt()).run();
            case EDIT -> new EditCommand(scan.nextInt(), scan.nextLine()).run();
            case AVG_AGE -> new AverageAgeCommand().run();
            case SELECT_ALL -> new SelectAllCommand().run();
            case SELECT -> new SelectCommand(scan.nextInt()).run();
            case HELP -> new HelpCommand().run();
        }

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
