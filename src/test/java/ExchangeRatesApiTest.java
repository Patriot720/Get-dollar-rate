import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.jupiter.api.Test;

public class ExchangeRatesApiTest {
    @Test
    public void testGetCurrency() throws MalformedURLException, IOException {
        ExchangeRatesEntity exchangeRateFromUSDToRub = ExchangeRatesApi.from("USD").to("RUB");
        assertTrue(exchangeRateFromUSDToRub.value > 70);
        assertEquals(exchangeRateFromUSDToRub.base,"USD");
    }
}
