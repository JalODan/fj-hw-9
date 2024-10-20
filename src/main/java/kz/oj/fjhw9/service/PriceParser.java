package kz.oj.fjhw9.service;

import jakarta.annotation.Nullable;
import kz.oj.fjhw9.constant.PriceRegularExpressions;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Service
public class PriceParser {

    private final List<Pattern> patterns;

    public PriceParser() {

        this.patterns = Stream.of(
                PriceRegularExpressions.CLOSED_RANGE_LITERAL,
                PriceRegularExpressions.CLOSED_RANGE_HYPHEN,
                PriceRegularExpressions.OPEN_RANGE_LEFT,
                PriceRegularExpressions.EXACT

        ).map(Pattern::compile).toList();

    }

    public Optional<Integer> parse(@Nullable String string) {

        if (string == null) {
            return Optional.empty();
        }

        return patterns.stream().map(pattern -> pattern.matcher(string))
                .filter(Matcher::matches)
                .map(this::min)
                .min(Integer::compareTo);
    }

    private int min(Matcher matcher) {

        int min = Integer.MAX_VALUE;

        for (int i = 1; i <= matcher.groupCount(); i++) {
            int group = Integer.parseInt(matcher.group(i));
            min = Integer.min(min, group);
        }

        return min;
    }

}
