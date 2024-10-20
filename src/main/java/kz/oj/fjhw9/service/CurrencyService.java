package kz.oj.fjhw9.service;

import kz.oj.fjhw9.data.Currency;
import kz.oj.fjhw9.data.CurrencyRate;
import kz.oj.fjhw9.data.DailyRatesResponse;
import kz.oj.fjhw9.feignclient.CurrencyClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class CurrencyService {

    private final CurrencyClient currencyClient;

    @Async
    public CompletableFuture<Double> convertToRubles(Double amount, Currency currency) {

        if (currency == Currency.RUB) {
            return CompletableFuture.completedFuture(amount);
        }

        DailyRatesResponse dailyRates = currencyClient.getDailyRates(LocalDate.now());
        CurrencyRate rate = dailyRates.getCurrencyRates().stream()
                .filter(cr -> Objects.equals(cr.getCharCode(), currency.name()))
                .findAny().orElseThrow(() -> new IllegalArgumentException("Currency not found"));

        return CompletableFuture.completedFuture(amount * rate.getRate());
    }
}
