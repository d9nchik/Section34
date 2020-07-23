package sample.exercise7;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class CreateQuestionTable {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/javaBook" +
                        "?serverTimezone=UTC", "scott",
                "(76%EtjM")) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Quiz  VALUES (?, ?, ?, ?, ?, ?, ?)");
            Scanner input = new Scanner(new URL("http://www.cs.armstrong.edu/liang/data/Quiz.txt")
                    .openStream());
            int counter = 1;
            while (input.hasNext()) {
                statement.setInt(1, counter++);
                String line = input.nextLine();
                statement.setString(2, line.substring(line.indexOf(" ") + 1));
                line = input.nextLine();
                statement.setString(3, line.substring(line.indexOf(" ") + 1));
                line = input.nextLine();
                statement.setString(4, line.substring(line.indexOf(" ") + 1));
                line = input.nextLine();
                statement.setString(5, line.substring(line.indexOf(" ") + 1));
                line = input.nextLine();
                statement.setString(6, line.substring(line.indexOf(" ") + 1));
                line = input.nextLine();
                statement.setString(7, line.substring(line.indexOf(":") + 1));
                statement.executeUpdate();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
