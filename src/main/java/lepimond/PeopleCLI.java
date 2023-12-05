package lepimond;

import com.mysql.cj.jdbc.Driver;
import lepimond.commands.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Properties;

import static lepimond.DBUtil.*;
import static lepimond.DBUtil.scan;

public class PeopleCLI {

    public PeopleCLI() throws Exception {
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

    private void readNextCommand() throws Exception {
        String currentCommand = scan.next();

        if (databaseExists(DB_NAME)) {
            if (tableExists(TABLE_NAME) || currentCommand.equals("/read") || currentCommand.equals("/help")) {
                Command command;
                switch (currentCommand) {
                    case "/read" -> command = new ReadCommand();
                    case "/delete" -> command = new DeleteCommand(scan.nextInt());
                    case "/delete_all" -> command = new DeleteAllCommand();
                    case "/insert" -> command = new InsertCommand(scan.next(), scan.next(), scan.nextInt());
                    case "/edit" -> command = new EditCommand(scan.nextInt(), scan.nextLine());
                    case "/avg_age" -> command = new AverageAgeCommand();
                    case "/select_all" -> command = new SelectAllCommand();
                    case "/select" -> command = new SelectCommand(scan.nextInt());
                    case "/help" -> command = new HelpCommand();
                    default -> throw new SQLException();
                }
                command.run();
            }
        }
    }
}
