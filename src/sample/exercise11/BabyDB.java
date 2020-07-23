package sample.exercise11;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class BabyDB {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/javaBook" +
                        "?serverTimezone=UTC", "scott",
                "(76%EtjM")) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Babyname  VALUES (?, ?, ?, ?)");
            for (int year = 2001; year <=2010 ; year++) {
                Scanner input = new Scanner(new URL("https://liveexample.pearsoncmg.com/data/babynamesranking"+
                        year+".txt").openStream());

                while(input.hasNext()){
                    String[] words= input.nextLine().split("\\s+");
                    statement.setInt(1, year);
                    statement.setString(2, words[1]);
                    statement.setString(3, "M");
                    statement.setString(4, words[2]);
                    statement.executeUpdate();

                    statement.setInt(1, year);
                    statement.setString(2, words[3]);
                    statement.setString(3, "F");
                    statement.setString(4, words[4]);
                    statement.executeUpdate();
                }

                input.close();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
