package com.quantity.measurement.repository;


import com.quantity.measurement.entity.QuantityMeasurementEntity;
import java.util.List;

public interface QuantityMeasurementRepository {
    void save(QuantityMeasurementEntity e);
    List<QuantityMeasurementEntity> getAllMeasurements();
    List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation);
    List<QuantityMeasurementEntity> getMeasurementsByType(String type);
    int getTotalCount();
    void deleteAll();
    boolean schemaExists();
    void forceError();
}