package kz.oj.fjhw9.web.rest.response;


import kz.oj.fjhw9.data.Event;

import java.util.List;

public record GetEventsResponse(List<Event> events) {
}
