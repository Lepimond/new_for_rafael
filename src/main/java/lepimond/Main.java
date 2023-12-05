package lepimond;

import com.mysql.cj.jdbc.Driver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Properties;

import static lepimond.DBUtil.*;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        new Main();
    }

    private Main() throws SQLException, IOException {
        readConfigs();

        DriverManager.registerDriver(new Driver());
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        stmt = conn.createStatement();
        if (databaseExists(DB_NAME)) {
            stmt.executeUpdate("DROP DATABASE " + DB_NAME);
        }

        stmt.executeUpdate("CREATE DATABASE " + DB_NAME);
        stmt.executeUpdate("USE " + DB_NAME);

        while(true) {
            try {
                this.readNextCommand();
            } catch (StringIndexOutOfBoundsException | InputMismatchException | SQLException var2) {
                System.out.println("Ошибка в синтаксисе команды! Попробуйте ещё раз");
            } catch (IOException var3) {
                System.out.println("JSON-файл инвалиден!");
            } catch (NullPointerException var4) {
                System.out.println("База данных пуста! Данная команда неприменима к пустой БД");
            }
        }
    }

    private void readConfigs() throws IOException {
        File configFile = new File("config.properties");

        FileReader reader = new FileReader(configFile);
        Properties props = new Properties();
        props.load(reader);

        FILE_NAME = props.getProperty("file_name");

        DB_NAME = props.getProperty("db_name");
        TABLE_NAME = props.getProperty("table_name");
        DB_URL = props.getProperty("db_url");
        USER = props.getProperty("user");
        PASS = props.getProperty("pass");

        reader.close();
    }

    private void readNextCommand() throws SQLException, IOException, StringIndexOutOfBoundsException, NullPointerException {
        String currentCommand = scan.next();

        if (databaseExists(DB_NAME)) {
            if (tableExists(TABLE_NAME) || currentCommand.equals("/read") || currentCommand.equals("/help")) {
                CommandType type;
                switch (currentCommand) {
                    case "/read" -> type = CommandType.READ;
                    case "/delete" -> type = CommandType.DELETE;
                    case "/delete_all" -> type = CommandType.DELETE_ALL;
                    case "/insert" -> type = CommandType.INSERT;
                    case "/edit" -> type = CommandType.EDIT;
                    case "/avg_age" -> type = CommandType.AVG_AGE;
                    case "/select_all" -> type = CommandType.SELECT_ALL;
                    case "/select" -> type = CommandType.SELECT;
                    case "/help" -> type = CommandType.HELP;
                    default -> throw new SQLException();
                }
                type.run();
            }
        }
    }
}