import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class Starter {
        public static void main(String[] args) throws SQLException, InterruptedException {
                String baseCurrency = "USD";
                String targetCurrency = "RUB";
                ExchangeRatesDatabase db = new ExchangeRatesDatabase();
                while (true) {
                        try {
                                ExchangeRatesEntity exchangeRate = ExchangeRatesApi.from(baseCurrency)
                                                .to(targetCurrency);
                                System.out.println(exchangeRate.value);
                                db.save(exchangeRate);
                        } catch (IOException e) {
                                System.out.println(db.getLastWithBase(baseCurrency));
                        }
                        TimeUnit.SECONDS.sleep(5);
                }
        }
}
