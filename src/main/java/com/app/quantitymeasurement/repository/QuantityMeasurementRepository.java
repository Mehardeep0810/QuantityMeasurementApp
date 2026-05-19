package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.model.QuantityMeasurementEntity;

import java.util.List;

public interface QuantityMeasurementRepository {
    <S extends QuantityMeasurementEntity> S save(S entity);
    List<QuantityMeasurementEntity> findAll();
    void deleteAll();
    long count();

    List<QuantityMeasurementEntity> findByMeasurementType(String measurementType);
    List<QuantityMeasurementEntity> findByOperation(String operation);
    long countByOperation(String operation);

    // helpers used by tests
    List<QuantityMeasurementEntity> getAllMeasurements();
    List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation);
    List<QuantityMeasurementEntity> getMeasurementsByType(String type);
    long getTotalCount();
    boolean schemaExists();
    void forceError();
}
