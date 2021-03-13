import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRatesDatabase {
    String databaseURL = "jdbc:ucanaccess://story.accdb";
    String insertQuery = "INSERT INTO EXCHANGE_RATES ([Check_date], [Ticker], [Rate]) VALUES (?, ?, ?)";
    Connection dbConnection;

    public void save(ExchangeRatesEntity entity) {
        try {
            dbConnection = DriverManager.getConnection(databaseURL);
            PreparedStatement preparedStatement = dbConnection.prepareStatement(insertQuery);
            preparedStatement.setDate(1, entity.date);
            preparedStatement.setString(2, entity.base);
            preparedStatement.setFloat(3, entity.value);
            preparedStatement.executeUpdate();
            dbConnection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public List<ExchangeRatesEntity> getByBase(String string) throws SQLException {
        dbConnection = DriverManager.getConnection(databaseURL);
        Statement statement = dbConnection.createStatement();
        ResultSet result = statement.executeQuery("SELECT * from EXCHANGE_RATES");
        List<ExchangeRatesEntity> entities = new ArrayList<ExchangeRatesEntity>();
        while (result.next()) {
            ExchangeRatesEntity entity = new ExchangeRatesEntity();
            entity.setBase(result.getString("Ticker"));
            entity.setDate(result.getDate("Check_date"));
            entity.setValue(result.getFloat("Rate"));
            entities.add(entity);
        }
        return entities;
    }

    public ExchangeRatesEntity getLastWithBase(String baseCurrency) throws SQLException {
        dbConnection = DriverManager.getConnection(databaseURL);
        Statement statement = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY);
        ResultSet result = statement.executeQuery("SELECT * from EXCHANGE_RATES");
        result.last();
        ExchangeRatesEntity entity = new ExchangeRatesEntity();
        entity.setBase(result.getString("Ticker"));
        entity.setDate(result.getDate("Check_date"));
        entity.setValue(result.getFloat("Rate"));
        return entity;
    }
}
