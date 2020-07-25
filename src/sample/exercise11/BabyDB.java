package sample.exercise11;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Scanner;

public class BabyDB {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/javaBook" +
                        "?serverTimezone=UTC", "scott",
                "(76%EtjM")) {

            Statement statement = connection.createStatement();
            for (int year = 2001; year <= 2010; year++) {
                Scanner input = new Scanner(new URL("https://liveexample.pearsoncmg.com/data/babynamesranking" +
                        year + ".txt").openStream());

                while (input.hasNext()) {
                    String[] words = input.nextLine().split("\\s+");
                    statement.addBatch("INSERT INTO Babyname VALUES (" + year + ", '" + words[1]
                            + "', 'M', '" + words[2] + "')");
                    statement.addBatch("INSERT INTO Babyname VALUES (" + year + ", '" + words[3]
                            + "', 'F', '" + words[4] + "')");
                }
                statement.executeBatch();
                input.close();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
