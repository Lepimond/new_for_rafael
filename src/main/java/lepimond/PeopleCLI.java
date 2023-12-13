package lepimond;

import lepimond.commands.*;
import lepimond.exceptions.PeopleCLIException;

import java.sql.*;

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
        } catch (SQLException e) {
            System.out.println("Ошибка при попытке создания базы данных!");
        } catch (PeopleCLIException e) {
            System.out.println(e.getMessage());;
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

    public static ResultSet executeQuery(String query) throws PeopleCLIException {
        try {
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            throw new PeopleCLIException("Ошибка в SQL-запросе", e);
        }
    }

    public static void executeUpdate(String update) throws PeopleCLIException {
        try {
            stmt.executeUpdate(update);
        } catch (SQLException e) {
            throw new PeopleCLIException("Ошибка в SQL-запросе", e);
        }
    }

    public static boolean databaseExists(String databaseName) throws PeopleCLIException {
        DatabaseMetaData meta;
        try {
            meta = conn.getMetaData();
        } catch (SQLException e) {
            throw new PeopleCLIException("Ошибка при проверке существования базы данных", e);
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
            throw new PeopleCLIException("Ошибка при проверке существования базы данных", e);
        }
    }

    public static boolean tableExists(String tableName) throws PeopleCLIException {
        DatabaseMetaData meta;
        try {
            meta = conn.getMetaData();
        } catch (SQLException e) {
            throw new PeopleCLIException("Ошибка при проверке существования таблицы", e);
        }
        try (ResultSet resultSet = meta.getTables(null, null, tableName, new String[]{"TABLE"})) {
            return resultSet.next();
        } catch (SQLException e) {
            throw new PeopleCLIException("Ошибка при проверке существования таблицы", e);
        }
    }
}
