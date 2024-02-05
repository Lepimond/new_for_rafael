package lepimond.commands;

import lepimond.PeopleCLI;
import lepimond.database_access.Person;
import lepimond.exceptions.PeopleCLIException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
            if (PeopleCLI.tableExists(TABLE_NAME)) {
                PeopleCLI.executeUpdate("DROP TABLE " + TABLE_NAME);
            }

            PeopleCLI.executeUpdate("""
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
                System.out.println("Inserted line " + i);
            }
    }

    private void insertLine(JSONArray json, int lineNumber) throws PeopleCLIException {
        JSONObject currentObject = json.getJSONObject(lineNumber);
        String firstName = currentObject.getString("first_name");
        String lastName = currentObject.getString("last_name");
        int age = currentObject.getInt("age");

        System.out.println(firstName + " " + lastName + " " + age);

        dao.save(new Person(firstName, lastName, age));
    }
}
