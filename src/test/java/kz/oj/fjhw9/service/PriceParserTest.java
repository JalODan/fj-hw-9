package kz.oj.fjhw9.service;

import kz.oj.fjhw9.constant.PriceRegularExpressions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class PriceParserTest {

    private final PriceParser priceParser = new PriceParser();

    @Test
    void givenNull_whenParse_thenReturnEmpty() {

        Optional<Integer> optional = priceParser.parse(null);
        assertTrue(optional.isEmpty());
    }

    @Test
    void givenArbitraryText_whenParse_thenReturnEmpty() {

        Optional<Integer> optional = priceParser.parse("уточняйте у организаторов");
        assertTrue(optional.isEmpty());
    }

    @Test
    void givenMereNumber_whenParse_thenReturnEmpty() {

        String string = "20";
        assertTrue(priceParser.parse(string).isEmpty());
    }

    @Test
    void givenNotFullMoneyWord_whenParse_thenReturnEmpty() {

        String string = "20 рубле";
        assertTrue(priceParser.parse(string).isEmpty());
    }

    @Test
    void givenShortMoneyWordWithoutDot_whenParse_thenReturnEmpty() {

        String string = "20 руб";
        assertTrue(priceParser.parse(string).isEmpty());
    }

    @Test
    void givenSuffixAndNoSpace_whenParse_thenReturnEmpty() {

        String string = "20 рублейчиков";
        assertTrue(priceParser.parse(string).isEmpty());
    }

    @Test
    void givenNoSpaceAfterSuffix_whenParse_thenReturnEmpty() {

        String string = "UTF-8 рублей";
        assertTrue(priceParser.parse(string).isEmpty());
    }

    @Test
    void givenClosedRangeLiteral_whenParse_thenReturn() {

        String string = "от 20 до 100 рублей";
        Optional<Integer> result = priceParser.parse(string);
        assertTrue(result.isPresent());
        assertEquals(20, result.get());
    }

    @Test
    void givenClosedRangeLiteralWithSpaceSeparatedSuffixAndPrefix_whenParse_thenReturn() {

        String string = "asdasd asd от 20 до 100 рублей asdsaads asdas";
        Optional<Integer> result = priceParser.parse(string);
        assertTrue(result.isPresent());
        assertEquals(20, result.get());
    }

    @Test
    void givenClosedRangeLiteralWithExtraSpaces_whenParse_thenReturn() {

        String string = "от 20   до    100 рублей";
        Optional<Integer> result = priceParser.parse(string);
        assertTrue(result.isPresent());
        assertEquals(20, result.get());
    }

    @Test
    void givenClosedRangeHyphen_whenParse_thenReturn() {

        String string = "20-100 рублей";
        Optional<Integer> result = priceParser.parse(string);
        assertTrue(result.isPresent());
        assertEquals(20, result.get());
    }

    @Test
    void givenClosedRangeHyphenWithSpaceSeparatedSuffixAndPrefix_whenParse_thenReturn() {

        String string = "adssadsas asdsad 20-100 рублей asdsad asda";
        Optional<Integer> result = priceParser.parse(string);
        assertTrue(result.isPresent());
        assertEquals(20, result.get());
    }

    @Test
    void givenClosedRangeHyphenWithExtraSpaces_whenParse_thenReturn() {

        String string = "20    -       100 рублей";
        Optional<Integer> result = priceParser.parse(string);
        assertTrue(result.isPresent());
        assertEquals(20, result.get());
    }

    @Test
    void givenOpenRangeLeft_whenParse_thenReturn() {

        String string = "от 20 рублей";
        Optional<Integer> result = priceParser.parse(string);
        assertTrue(result.isPresent());
        assertEquals(20, result.get());
    }

    @Test
    void givenOpenRangeLeftWithSpaceSeparatedSuffixAndPrefix_whenParse_thenReturn() {

        String string = "asdasdas dassd asds от 20 рублей asdasdasd ads";
        Optional<Integer> result = priceParser.parse(string);
        assertTrue(result.isPresent());
        assertEquals(20, result.get());
    }

    @Test
    void givenExact_whenParse_thenReturn() {

        String string = "20 рублей";
        Optional<Integer> result = priceParser.parse(string);
        assertTrue(result.isPresent());
        assertEquals(20, result.get());
    }

    @Test
    void givenExactWithSpaceSeparatedSuffixAndPrefix_whenParse_thenReturn() {

        String string = "asdasd asd asd sa 20 рублей as dsa ";
        Optional<Integer> result = priceParser.parse(string);
        assertTrue(result.isPresent());
        assertEquals(20, result.get());
    }

    @Test
    void givenExactWithExtraSpaces_whenParse_thenReturn() {

        String string = "20         рублей";
        Optional<Integer> result = priceParser.parse(string);
        assertTrue(result.isPresent());
        assertEquals(20, result.get());
    }

    @Test
    void givenMultipleMatches_thenParse_thenReturnLowest() {

        String string = "400 рублей, льготный билет — 300 рублей";
        Optional<Integer> result = priceParser.parse(string);
        assertTrue(result.isPresent());
        assertEquals(300, result.get());
    }

}