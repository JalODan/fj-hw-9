package kz.oj.fjhw9.feignclient.event;

import kz.oj.fjhw9.data.EventsPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class EventClientTest {

    @InjectMocks
    private EventClient eventClient;

    @Mock
    private EventClientInternal eventClientInternal;

    @Test
    void countPages() throws ExecutionException, InterruptedException {

        // Given
        EventsPage eventsPage = mock(EventsPage.class);
        when(eventsPage.count()).thenReturn(2930);

        when(eventClientInternal.getPage(
                anyString(),
                anyString(),
                anyString(),
                anyInt(),
                anyInt()
        )).thenReturn(eventsPage);

        // When
        CompletableFuture<Integer> pageCount = eventClient.countPages(LocalDate.now(), LocalDate.now());

        // Then
        assertEquals(30, pageCount.get());
    }
}