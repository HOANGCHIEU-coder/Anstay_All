package com.anstay.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.text.DecimalFormat;

public class MoneySerializer extends JsonSerializer<Double> {
    private static final DecimalFormat df = new DecimalFormat("#,##0.00");

    @Override
    public void serialize(Double value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        if (value != null) {
            gen.writeNumber(df.format(value).replace(",", ""));
        } else {
            gen.writeNull();
        }
    }
}