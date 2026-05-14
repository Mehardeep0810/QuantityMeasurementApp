package com.app.quantitymeasurement.entity;

import java.time.LocalDateTime;

public class QuantityMeasurementEntity {
    private String measurementType;
    private String operation;
    private String operand;
    private String result;
    private LocalDateTime timestamp;

    public QuantityMeasurementEntity(String measurementType, String operation, String operand, String result) {
        this.measurementType = measurementType;
        this.operation = operation;
        this.operand = operand;
        this.result = result;
        this.timestamp = LocalDateTime.now();
    }

    public String getMeasurementType() { return measurementType; }
    public String getOperation() { return operation; }
    public String getOperand() { return operand; }
    public String getResult() { return result; }
    public LocalDateTime getTimestamp() { return timestamp; }
}