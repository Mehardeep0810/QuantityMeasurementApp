package com.quantity.measurement.repository;

import com.quantity.measurement.entity.QuantityMeasurementEntity;
import java.util.List;

/**
 * UC15-compliant repository interface.
 * Defines minimal, focused operations for persistence.
 */
public interface QuantityMeasurementRepository {
    /**
     * Save a QuantityMeasurementEntity to the repository.
     * @param entity the entity to persist
     */
    void save(QuantityMeasurementEntity entity);

    /**
     * Retrieve all persisted entities.
     * @return unmodifiable list of entities
     */
    List<QuantityMeasurementEntity> findAll();

    /**
     * Clear all persisted entities.
     */
    void clear();
}
