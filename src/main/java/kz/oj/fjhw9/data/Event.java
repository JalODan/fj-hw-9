package kz.oj.fjhw9.data;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Event(String title, String price, @JsonProperty("site_url") String siteUrl) {
}
