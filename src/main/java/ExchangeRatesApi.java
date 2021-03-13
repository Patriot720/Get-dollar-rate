import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.concurrent.TimeUnit;

public class ExchangeRatesApi {
    String baseCurrency;
    String targetCurrency;
    ExchangeRatesEntity exchangeRates = new ExchangeRatesEntity();
    DatabaseEntity database = new DatabaseEntity();

    String link = "https://api.exchangeratesapi.io/latest?base=";
    URL url;

    public ExchangeRatesApi(String baseCurrency, String targetCurrency) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        try {
            url = new URL(link + baseCurrency);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void getCurrency() {
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
            exchangeRates.parseJSON(response.toString(), targetCurrency);
            database.save(exchangeRates.date, exchangeRates.base, exchangeRates.value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public float getLatestExchangeRate() {
        return exchangeRates.value;
    }
}
