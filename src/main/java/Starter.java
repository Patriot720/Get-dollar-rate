import java.util.concurrent.TimeUnit;

public class Starter {
    public static void main(String[] args) {
        String baseCurrency = "USD";
        String targetCurrency = "RUB";
        ExchangeRatesApi api = new ExchangeRatesApi(baseCurrency, targetCurrency);
        while (true) {
            api.getCurrency();
            System.out.println(api.getLatestExchangeRate());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
