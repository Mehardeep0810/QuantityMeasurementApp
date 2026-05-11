package com.quantity.measurement.repository;

import com.quantity.measurement.entity.QuantityMeasurementEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * UC15-compliant QuantityMeasurementCacheRepository.
 * Singleton in-memory repository with optional disk persistence.
 * Implements IQuantityMeasurementRepository interface.
 */
public class QuantityMeasurementCacheRepository implements QuantityMeasurementRepository {

    private static final String STORE_FILE = "quantity_repo.dat";
    private static final QuantityMeasurementCacheRepository INSTANCE =
            new QuantityMeasurementCacheRepository();

    private final List<QuantityMeasurementEntity> store = Collections.synchronizedList(new ArrayList<>());

    private QuantityMeasurementCacheRepository() {
        // Attempt to load persisted entities from disk
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STORE_FILE))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                List<?> list = (List<?>) obj;
                for (Object o : list) {
                    if (o instanceof QuantityMeasurementEntity) {
                        store.add((QuantityMeasurementEntity) o);
                    }
                }
            }
        } catch (Exception ignored) {
            // No file or incompatible format — start with empty store
        }
    }

    public static QuantityMeasurementCacheRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        store.add(entity);
        persist();
    }

    @Override
    public List<QuantityMeasurementEntity> findAll() {
        return Collections.unmodifiableList(store);
    }

    @Override
    public void clear() {
        store.clear();
        try {
            new File(STORE_FILE).delete();
        } catch (Exception ignored) {}
    }

    private void persist() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STORE_FILE))) {
            oos.writeObject(new ArrayList<>(store));
        } catch (IOException ignored) {
            // Best-effort persistence
        }
    }
}
