package lepimond.commands;

import lepimond.DBUtil;
import lepimond.database_access.Person;
import lepimond.exceptions.PeopleCLIException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

import static lepimond.DBUtil.*;

public class ReadCommand implements Command {
    @Override
    public void run() throws PeopleCLIException {
        makeTable(readFile(FILE_NAME));
    }

    private String readFile(String path) throws PeopleCLIException {
        Path fileName = Path.of(path);
        try {
            return Files.readString(fileName);
        } catch (IOException e) {
            throw new PeopleCLIException("Ошибка при чтении файла", e);
        }
    }

    private void makeTable(String jsonInput) throws PeopleCLIException {
        try {
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
        } catch (SQLException e) {
            throw new PeopleCLIException("При попытке создания таблицы произошла ошибка, что-то не так с SQL-запросом", e);
        }

    }

    private void insertLine(JSONArray json, int lineNumber) throws PeopleCLIException {
        JSONObject currentObject = json.getJSONObject(lineNumber);
        String firstName = currentObject.getString("first_name");
        String lastName = currentObject.getString("last_name");
        int age = currentObject.getInt("age");
        dao.save(new Person(firstName, lastName, age));
    }
}
