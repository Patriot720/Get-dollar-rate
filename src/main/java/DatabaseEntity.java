import java.sql.*;

public class DatabaseEntity {
    String databaseURL = "jdbc:ucanaccess://D:/Programming/java/Projects/getDollarRate/story.accdb";
    String insertQuery = "INSERT INTO EXCHANGE_RATES ([Check_date], [Ticker], [Rate]) VALUES (?, ?, ?)";
    Connection dbConnection;

    public void save(Date date, String base, float value) {
        try {
            dbConnection = DriverManager.getConnection(databaseURL);
            PreparedStatement preparedStatement = dbConnection.prepareStatement(insertQuery);
            preparedStatement.setDate(1, date);
            preparedStatement.setString(2, base);
            preparedStatement.setFloat(3, value);
            preparedStatement.executeUpdate();
            dbConnection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
