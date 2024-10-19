package kz.oj.fjhw9.task;

import kz.oj.fjhw9.feignclient.LocationClient;
import kz.oj.fjhw9.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;

@Component
@RequiredArgsConstructor
public class LocationsUpdateTask implements Callable<Void> {

    private final LocationService locationService;
    private final LocationClient locationClient;

    @Override
    public Void call() {

        locationService.update(locationClient.findAll());
        return null;
    }

}
