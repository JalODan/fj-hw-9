package kz.oj.fjhw9.data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import kz.oj.fjhw9.configuration.CustomDoubleDeserializer;
import lombok.Data;

@Data
public class CurrencyRate {

    @JacksonXmlProperty(localName = "ID", isAttribute = true)
    private String id;

    @JacksonXmlProperty(localName = "NumCode")
    private String numCode;

    @JacksonXmlProperty(localName = "CharCode")
    private String charCode;

    @JacksonXmlProperty(localName = "Nominal")
    private Integer nominal;

    @JacksonXmlProperty(localName = "Name")
    private String name;

    @JsonDeserialize(using = CustomDoubleDeserializer.class)
    @JacksonXmlProperty(localName = "Value")
    private Double value;

    @JsonDeserialize(using = CustomDoubleDeserializer.class)
    @JacksonXmlProperty(localName = "VunitRate")
    private Double rate;
}