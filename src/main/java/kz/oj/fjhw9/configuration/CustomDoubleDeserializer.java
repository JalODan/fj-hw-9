package kz.oj.fjhw9.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class CustomDoubleDeserializer extends JsonDeserializer<Double> {

    @Override
    public Double deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String value = p.getText();
        try {
            NumberFormat format = NumberFormat.getInstance(Locale.FRANCE); // uses comma as decimal separator
            Number number = format.parse(value);
            return number.doubleValue();
        } catch (Exception e) {
            throw new IOException("Invalid double value: " + value, e);
        }
    }
}