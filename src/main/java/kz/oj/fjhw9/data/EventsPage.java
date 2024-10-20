package kz.oj.fjhw9.data;

import java.util.List;

public record EventsPage(int count, String next, List<Event> results) {
}
