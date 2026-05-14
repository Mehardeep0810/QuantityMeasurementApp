package com.quantity.measurement.repoImpl;

import com.quantity.measurement.entity.QuantityMeasurementEntity;
import com.quantity.measurement.repository.QuantityMeasurementRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class QuantityMeasurementCacheRepository implements QuantityMeasurementRepository {
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