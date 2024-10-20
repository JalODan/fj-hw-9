package kz.oj.fjhw9.constant;

public interface PriceRegularExpressions {

    String MONEY = "\\s*(?:рублей|руб\\.|р\\.)";
    String PREFIX = "(?:.*\\s+)?";
    String SUFFIX = "(?:\\s+.*)?";
    String CLOSED_RANGE_LITERAL = PREFIX + "от\\s*(\\d+)\\s*до\\s*\\d+" + MONEY + SUFFIX; // от 2000 до 6000 рублей
    String CLOSED_RANGE_HYPHEN = PREFIX + "(\\d+)\\s*[-\\–]\\s*\\d+" + MONEY + SUFFIX; // 700–2500 рублей
    String OPEN_RANGE_LEFT = PREFIX + "от\\s*(\\d+)\\s*" + MONEY + SUFFIX; // от 1500 рублей
    String EXACT = PREFIX + "(\\d+)\\s*" + MONEY + SUFFIX; // 1500 руб.

}
