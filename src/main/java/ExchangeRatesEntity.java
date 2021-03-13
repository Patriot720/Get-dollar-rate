import org.json.JSONObject;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ExchangeRatesEntity {
    private JSONObject rates;
    String base;
    Date date;
    float value;

    public void parseJSON(String request, String targetCurrency) {
        JSONObject tmp = new JSONObject(request.toString());

        rates = tmp.getJSONObject("rates");
        base = tmp.getString("base");
        value = rates.getFloat(targetCurrency);

        java.util.Date utilDate = null;
        try {
            utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(tmp.getString("date"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        date = new java.sql.Date(utilDate.getTime());
    }
}
