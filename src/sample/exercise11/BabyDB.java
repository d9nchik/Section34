package sample.exercise11;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BabyDB {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/javaBook" +
                        "?serverTimezone=UTC", "scott",
                "(76%EtjM")) {
            ExecutorService service = Executors.newCachedThreadPool();
            Statement statement = connection.createStatement();

            for (int year = 2001; year < 2010; year++) {
                int finalYear = year;
                service.execute(()-> addNamesToDB(statement, finalYear));
            }

            service.shutdown();
            addNamesToDB(statement, 2010);

            while(!service.isTerminated()){
                statement.executeBatch();
                Thread.sleep(1_000);
            }
            statement.executeBatch();
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void addNamesToDB(Statement statement, int finalYear) {
        try(
        Scanner input = new Scanner(new URL("https://liveexample.pearsoncmg.com/data/babynamesranking" +
                finalYear + ".txt").openStream())){
            while (input.hasNext()) {
                String[] words = input.nextLine().split("\\s+");
                synchronized (statement) {
                    statement.addBatch("INSERT INTO Babyname VALUES (" + finalYear + ", '" + words[1]
                            + "', 'M', '" + words[2] + "')");
                    statement.addBatch("INSERT INTO Babyname VALUES (" + finalYear + ", '" + words[3]
                            + "', 'F', '" + words[4] + "')");
                }
            }
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }
}
