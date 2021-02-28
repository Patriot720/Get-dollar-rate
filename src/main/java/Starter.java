import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.concurrent.TimeUnit;

public class Starter {
    String response;
    String baseCurrency = "USD";
    String databaseURL = "jdbc:ucanaccess://D:/Programming/java/getDollarRate/story.accdb";
    String sql = "INSERT INTO EXCHANGE_RATES ([Check_date], [Ticker], [Rate]) VALUES (?, ?, ?)";

    public static void main(String[] args) {
        Starter starter = new Starter();
        starter.go();
    }

    public void go() {
        try {
            URL url = new URL("https://api.exchangeratesapi.io/latest?base=" + baseCurrency);
            Connection dbConnection = DriverManager.getConnection(databaseURL);

            while (true) {
                this.getResponse(url);

                ExchangeRatesEntity exchangeRatesEntity = new ExchangeRatesEntity(response.toString());

                PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);

                preparedStatement.setDate(1, exchangeRatesEntity.date);
                preparedStatement.setString(2, exchangeRatesEntity.base);
                preparedStatement.setFloat(3, exchangeRatesEntity.value);

                int row = preparedStatement.executeUpdate();
                if (row > 0) {
                    System.out.println("A row has been inserted successfully.");
                }
                TimeUnit.HOURS.sleep(1);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void getResponse(URL url) {
        HttpURLConnection connection;
        InputStream receive;
        BufferedReader receiveBuffer;
        StringBuilder response;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:85.0) Gecko/20100101 Firefox/85.0");
            receive = connection.getInputStream();
            receiveBuffer = new BufferedReader(new InputStreamReader(receive));
            response = new StringBuilder();

            String line;
            while ((line = receiveBuffer.readLine()) != null) {
                response.append(line);
            }
            receiveBuffer.close();

            this.response = response.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
