package lepimond;

import lepimond.commands.*;
import lepimond.exceptions.PeopleCLIException;
import lepimond.services.I18n;
import org.apache.logging.log4j.Level;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.function.Consumer;

import static lepimond.DBUtil.*;
import static lepimond.DBUtil.scan;
import static lepimond.config.PeopleCLIConfiguration.*;

public class PeopleCLI {

    private static Statement stmt;
    private static Connection conn;

    public PeopleCLI() {
        try {
            readConfigs();

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            useLogger("Program started");

            if (databaseExists(DB_NAME)) {
                stmt.executeUpdate("DROP DATABASE " + DB_NAME);
            }

            stmt.executeUpdate("CREATE DATABASE " + DB_NAME);
            stmt.executeUpdate("USE " + DB_NAME);

            while(true) {
                try {
                    useLogger("Reading command");
                    readNextCommand();
                } catch (PeopleCLIException | RuntimeException e) {
                    System.out.println(I18n.getMessage("error_sql_query"));
                    useLogger(e.getMessage());
                }

            }
        } catch (SQLException e) {
            System.out.println(I18n.getMessage("error_creating_db"));
            useLogger(e.getMessage());
        } catch (PeopleCLIException e) {
            System.out.println(I18n.getMessage("error_sql_query"));
            useLogger(e.getMessage());
        } finally {
            closeScanner();
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
                    case "/edit" -> command = new EditCommand(scan.nextInt(), scan.next());
                    case "/avg_age" -> command = new AverageAgeCommand();
                    case "/select_all" -> command = new SelectAllCommand();
                    case "/select" -> command = new SelectCommand(scan.nextInt());
                    case "/help" -> command = new HelpCommand();
                    default -> throw new PeopleCLIException(I18n.getMessage("no_such_command"));
                }
                command.run();
            }
        }
    }

    public static void executeQuery(String query, Consumer resultProcessor) throws PeopleCLIException {
        try (ResultSet resultSet = stmt.executeQuery(query)) {
            resultProcessor.accept(resultSet);
        } catch (SQLException e) {
            throw new PeopleCLIException("Ошибка в SQL-запросе");
        }
    }

    public static void executeUpdate(String update) throws PeopleCLIException {
        try {
            stmt.executeUpdate(update);
        } catch (SQLException e) {
            throw new PeopleCLIException(I18n.getMessage("error_sql_query"), e);
        }
    }

    public static boolean databaseExists(String databaseName) throws PeopleCLIException {
        DatabaseMetaData meta;
        try {
            meta = conn.getMetaData();
        } catch (SQLException e) {
            throw new PeopleCLIException(I18n.getMessage("error_check_db"), e);
        }
        try (ResultSet resultSet = meta.getCatalogs()) {
            String currentName;
            do {
                if (!resultSet.next()) {
                    return false;
                }

                currentName = resultSet.getString(1);
            } while(!currentName.equals(databaseName));

            return true;
        } catch (SQLException e) {
            throw new PeopleCLIException(I18n.getMessage("error_check_db"), e);
        }
    }

    public static boolean tableExists(String tableName) throws PeopleCLIException {
        DatabaseMetaData meta;
        try {
            meta = conn.getMetaData();
        } catch (SQLException e) {
            throw new PeopleCLIException(I18n.getMessage("error_check_table"), e);
        }
        try (ResultSet resultSet = meta.getTables(null, null, tableName, new String[]{"TABLE"})) {
            return resultSet.next();
        } catch (SQLException e) {
            throw new PeopleCLIException(I18n.getMessage("error_check_table"), e);
        }
    }

    private static void useLogger(String message) {
        switch (LOG_LEVEL) {
            case "trace" -> logger.trace(message);
            case "info" -> logger.info(message);
            case "debug" -> logger.debug(message);
            case "error" -> logger.error(message);
            case "warn" -> logger.warn(message);
        }
    }
}
