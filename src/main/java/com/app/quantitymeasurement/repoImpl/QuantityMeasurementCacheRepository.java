
package com.app.quantitymeasurement.repoImpl;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;


import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class QuantityMeasurementCacheRepository implements QuantityMeasurementRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuantityMeasurementCacheRepository.class);
    private static final QuantityMeasurementCacheRepository INSTANCE = new QuantityMeasurementCacheRepository();
    private final List<QuantityMeasurementEntity> store = Collections.synchronizedList(new ArrayList<>());

    private QuantityMeasurementCacheRepository() { }

    public static QuantityMeasurementCacheRepository getInstance() {
        return INSTANCE;
    }

    @Override

    public void save(QuantityMeasurementEntity e) { store.add(e); }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() { return new ArrayList<>(store); }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
        return store.stream().filter(e -> e.getOperation().equalsIgnoreCase(operation)).collect(Collectors.toList());
    public void save(QuantityMeasurementEntity e) {
        LOGGER.debug("Saving entity in cache: {}", e);
        store.add(e);
        LOGGER.info("Entity saved successfully. Current cache size: {}", store.size());
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        LOGGER.debug("Fetching all measurements from cache");
        return new ArrayList<>(store);
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
        LOGGER.debug("Fetching measurements by operation: {}", operation);
        return store.stream()
                .filter(e -> e.getOperation().equalsIgnoreCase(operation))
                .collect(Collectors.toList());
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByType(String type) {
        return store.stream().filter(e -> e.getMeasurementType().equalsIgnoreCase(type)).collect(Collectors.toList());
    }

    @Override
    public int getTotalCount() { return store.size(); }

    @Override
    public void deleteAll() { store.clear(); }

    @Override
    public boolean schemaExists() { return true; }

    @Override
    public void forceError() { throw new RuntimeException("Forced error in CacheRepository"); }
}
        LOGGER.debug("Fetching measurements by type: {}", type);
        return store.stream()
                .filter(e -> e.getMeasurementType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    @Override
    public int getTotalCount() {
        int count = store.size();
        LOGGER.info("Total count of entities in cache: {}", count);
        return count;
    }

    @Override
    public void deleteAll() {
        LOGGER.warn("Deleting all entities from cache");
        store.clear();
    }

    @Override
    public boolean schemaExists() {
        LOGGER.debug("Schema check called on cache repository (always true)");
        return true;
    }

    @Override
    public void forceError() {
        LOGGER.error("Forced error triggered in CacheRepository");
        throw new RuntimeException("Forced error in CacheRepository");
    }
}
