package sample.exercise1;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.*;

public class StaffController {
    @FXML
    private TextField id, last, first, mi, address, city, state, telephone;

    @FXML
    private Text message;

    private PreparedStatement view, insert, update, clear;
    private Connection connection;

    @FXML
    public void initialize() {
        new Thread(() -> {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://localhost/javaBook" +
                                "?serverTimezone=UTC", "scott",
                        "(76%EtjM");

                view = connection.prepareStatement("SELECT * FROM Staff WHERE ? = id;");
                insert = connection.prepareStatement("INSERT Into Staff (id, lastName, firstName, mi," +
                        " address, city, state, telephone) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
                update = connection.prepareStatement("UPDATE Staff SET lastName=?, firstName=?, mi=?, address=?," +
                        "city=?, state=?, telephone=? WHERE id=?");
                clear = connection.prepareStatement("DELETE FROM Staff WHERE id=?");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(() -> {//close resource
            while (id.getScene() == null||id.getScene().getWindow()==null) {
                Thread.yield();
            }
            id.getScene().getWindow().setOnCloseRequest(event -> {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            });
        }).start();
    }

    @FXML
    public void view() {
        try {
            view.setString(1, id.getText());
            ResultSet resultSet = view.executeQuery();
            if (resultSet.next()) {
                last.setText(resultSet.getString(2));
                first.setText(resultSet.getString(3));
                mi.setText(resultSet.getString(4));
                address.setText(resultSet.getString(5));
                city.setText(resultSet.getString(6));
                state.setText(resultSet.getString(7));
                telephone.setText(resultSet.getString(8));
            } else {
                message.setText("ID not found.");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void insert() {
        try {
            insert.setString(1, id.getText());
            insert.setString(2, last.getText());
            insert.setString(3, first.getText());
            insert.setString(4, mi.getText());
            insert.setString(5, address.getText());
            insert.setString(6, city.getText());
            insert.setString(7, state.getText());
            insert.setString(8, telephone.getText());
            insert.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void update() {
        try {
            update.setString(8, id.getText());
            update.setString(1, last.getText());
            update.setString(2, first.getText());
            update.setString(3, mi.getText());
            update.setString(4, address.getText());
            update.setString(5, city.getText());
            update.setString(6, state.getText());
            update.setString(7, telephone.getText());
            update.executeUpdate();
            message.setText("Successfully updated");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void clear() {
        try {
            clear.setString(1, id.getText());
            clear.executeUpdate();
            message.setText("Cleared successfully");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
