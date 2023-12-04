package lepimond;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONObject;

import static lepimond.DBUtil.*;

public enum CommandType {
    READ, DELETE, DELETE_ALL, INSERT, EDIT, AVG_AGE, SELECT_ALL, SELECT, HELP;

    public void run() throws SQLException, IOException {
        switch (this) {
            case READ -> makeTable(readFile(scan.nextLine().substring(1)));
            case DELETE -> deleteId(scan.nextInt());
            case DELETE_ALL -> deleteAll();
            case INSERT -> insert(scan.next(), scan.next(), scan.nextInt());
            case EDIT -> editId(scan.nextInt(), scan.nextLine());
            case AVG_AGE -> System.out.println(avgAge());
            case SELECT_ALL -> printResult(selectAll());
            case SELECT -> printResult(selectID(scan.nextInt()));
            case HELP -> printHelp();
        }

    }

    private void printHelp() {
        System.out.println("/read <название_файла>\n\tСчитывает JSON-файл со списком людей и записывает его в локальную реляционную БД\n/delete <id>\n\tУдаляет из БД запись с указанным id\n/delete_all\n\tУдаляет вообще все записи в БД\n/insert <имя>, <фамилия>, <возраст>\n\tВставляет в БД новую запись \"имя, фамилия, возраст\"\n/edit <id> <атрибут_1>=\"<значение_1>\", <атрибут_2>=\"значение_2\", ...\n\tИзменяет содержимое отдельных атрибутов отдельно взятой записи\n/avg_age\n\tВыводит средний возраст всех людей в БД\n/select_all\n\tВыводит все записи, наличествующие в БД\n/select <id>\n\tВыводит из БД запись с конкретным id");
    }

    private void printResult(ResultSet result) throws SQLException {
        System.out.printf("----------------------------------------------------%n");

        System.out.printf("|  id  |   first_name    |    last_name    |  age  |%n");
        System.out.printf("----------------------------------------------------%n");

        while(result.next()) {
            System.out.printf("| %04d | %-15s | %-15s | %04d |%n",
                    result.getInt(1),
                    result.getString(2),
                    result.getString(3),
                    result.getInt(4));
            System.out.printf("----------------------------------------------------%n");
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

        stmt.executeUpdate("CREATE TABLE people " +
                "(\n    id int AUTO_INCREMENT,\n    " +
                "first_name varchar(255),\n    " +
                "last_name varchar(255),\n    " +
                "age int,\nPRIMARY KEY(id)\n);");
        JSONArray json = new JSONArray(jsonInput);

        for(int i = 0; i < json.length(); ++i) {
            JSONObject currentObject = json.getJSONObject(i);
            String firstName = currentObject.getString("first_name");
            String lastName = currentObject.getString("last_name");
            int age = currentObject.getInt("age");
            this.insert(firstName, lastName, age);
        }

    }

    private ResultSet selectID(int id) throws SQLException {
        return stmt.executeQuery("SELECT * FROM " + TABLE_NAME + " WHERE id = " + id);
    }

    private ResultSet selectAll() throws SQLException {
        return stmt.executeQuery("SELECT * FROM " + TABLE_NAME);
    }

    private void insert(String first_name, String last_name, int age) throws SQLException {
        stmt.executeUpdate("INSERT INTO " + TABLE_NAME + " (first_name, last_name, age)\n" +
                "VALUES (\"" + first_name + "\", \"" + last_name + "\", " + age + ")");
    }

    private void deleteAll() throws SQLException {
        stmt.executeUpdate("TRUNCATE " + TABLE_NAME);
    }

    private void deleteId(int id) throws SQLException {
        stmt.executeUpdate("DELETE FROM " + TABLE_NAME + " WHERE id = " + id);
    }

    private void editId(int id, String edit) throws SQLException {
        stmt.executeUpdate("UPDATE " + TABLE_NAME + " SET " + edit + " WHERE id = " + id);
    }

    private double avgAge() throws SQLException, NullPointerException {
        ResultSet result = stmt.executeQuery("SELECT AVG(age) AS average_age FROM " + TABLE_NAME);
        if (result.next()) {
            String str = result.getString("average_age");
            return Double.parseDouble(str);
        } else {
            return -1.0;
        }
    }
}
