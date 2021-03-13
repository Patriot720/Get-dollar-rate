import org.json.JSONObject;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ExchangeRatesEntity {
    private JSONObject rates;
    String base;
    Date date;
    float value;

    public static ExchangeRatesEntity fromJSON(String request, String targetCurrency) {
        JSONObject tmp = new JSONObject(request.toString());
        ExchangeRatesEntity entity = new ExchangeRatesEntity();
        entity.rates = tmp.getJSONObject("rates");
        entity.base = tmp.getString("base");
        entity.value = entity.rates.getFloat(targetCurrency);

        java.util.Date utilDate = null;
        try {
            utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(tmp.getString("date"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        entity.date = new java.sql.Date(utilDate.getTime());
        return entity;
    }

	public void setBase(String base) {
        this.base = base;
	}

	public void setDate(Date date) {
        this.date = date;
	}

	public void setValue(float value) {
        this.value = value;
	}
}
