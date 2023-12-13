package lepimond;

import lepimond.commands.*;
import lepimond.exceptions.PeopleCLIException;

import java.sql.DriverManager;

import static lepimond.DBUtil.*;
import static lepimond.DBUtil.scan;
import static lepimond.config.PeopleCLIConfiguration.*;

public class PeopleCLI {

    public PeopleCLI() throws Exception {
        readConfigs();

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            if (databaseExists(DB_NAME)) {
                stmt.executeUpdate("DROP DATABASE " + DB_NAME);
            }

            stmt.executeUpdate("CREATE DATABASE " + DB_NAME);
            stmt.executeUpdate("USE " + DB_NAME);

            while(true) {
                try {
                    readNextCommand();
                } catch (PeopleCLIException e) {
                    System.out.println(e.getMessage());
                }
            }
        } finally {
            conn.close();
            stmt.close();
        }

    }

    private void readNextCommand() throws PeopleCLIException {
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
                    default -> throw new PeopleCLIException("У нас нет такой команды!");
                }
                command.run();
            }
        }
    }
}
