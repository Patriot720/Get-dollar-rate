
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ExchangeRatesDatabaseTest {
    @Test
    public void testSaveExchangeRateToDb() throws SQLException {
        ExchangeRatesEntity exchangeRatesEntity = new ExchangeRatesEntity();
        exchangeRatesEntity.setBase("USD");
        exchangeRatesEntity.setDate(new java.sql.Date(new Date().getTime()));
        exchangeRatesEntity.setValue((float) 78.25);
        ExchangeRatesDatabase db = new ExchangeRatesDatabase();
        int initialSize = db.getByBase("USD").size();
        db.save(exchangeRatesEntity);
        List<ExchangeRatesEntity> exchangeRates = db.getByBase("USD");
        assertTrue(exchangeRates.size() > initialSize);
        ExchangeRatesEntity last = db.getLastWithBase("USD");
        assertEquals(last.value, 78.0);

    }

}
