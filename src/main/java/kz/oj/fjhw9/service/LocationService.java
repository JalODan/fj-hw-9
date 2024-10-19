package kz.oj.fjhw9.service;

import kz.oj.fjhw9.data.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final ConcurrentHashMap.KeySetView<Location, Boolean> locations = ConcurrentHashMap.newKeySet();

    public void update(List<Location> newLocations) {

        this.locations.clear();
        this.locations.addAll(newLocations);
    }
}
