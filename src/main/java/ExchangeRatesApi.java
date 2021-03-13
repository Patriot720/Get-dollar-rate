import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ExchangeRatesApi {
    String baseCurrency;
    String targetCurrency;

    String link = "https://api.exchangeratesapi.io/latest?base=";

    public ExchangeRatesApi(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public ExchangeRatesApi(String baseCurrency, String targetCurrency) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
    }

    public static ExchangeRatesApi from(String baseCurrency) {
        return new ExchangeRatesApi(baseCurrency);
    }

    public ExchangeRatesEntity to(String targetCurrency) throws MalformedURLException, IOException {
        return fromTo(this.baseCurrency, targetCurrency);
    }

    private ExchangeRatesEntity fromTo(String baseCurrency, String targetCurrency)
            throws MalformedURLException, IOException {
        HttpURLConnection connection;
        InputStream receive;
        BufferedReader receiveBuffer;
        StringBuilder response;

        connection = (HttpURLConnection) getUrlFrom(baseCurrency).openConnection();
        connection.setRequestProperty("user-agent",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:85.0) Gecko/20100101 Firefox/85.0");
        receive = connection.getInputStream();
        receiveBuffer = new BufferedReader(new InputStreamReader(receive));
        response = new StringBuilder();

        String line;
        while ((line = receiveBuffer.readLine()) != null) {
            response.append(line);
        }
        receiveBuffer.close();
        return ExchangeRatesEntity.fromJSON(response.toString(), targetCurrency);
    }

    private URL getUrlFrom(String baseCurrency) throws MalformedURLException {
        return new URL(link + baseCurrency);
    }
}
