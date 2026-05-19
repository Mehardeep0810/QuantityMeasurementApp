package com.app.quantitymeasurement.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class QuantityMeasurementEntity {

    private Long id;
    private String measurementType;
    private String operation;
    private String input;
    private String result;
    private LocalDateTime timestamp;

    public QuantityMeasurementEntity() { }

    public QuantityMeasurementEntity(String measurementType, String operation, String input, String result) {
        this.measurementType = measurementType;
        this.operation = operation;
        this.input = input;
        this.result = result;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMeasurementType() { return measurementType; }
    public void setMeasurementType(String measurementType) { this.measurementType = measurementType; }

    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }

    public String getInput() { return input; }
    public void setInput(String input) { this.input = input; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuantityMeasurementEntity that = (QuantityMeasurementEntity) o;
        return Objects.equals(id, that.id)
                && Objects.equals(measurementType, that.measurementType)
                && Objects.equals(operation, that.operation)
                && Objects.equals(input, that.input)
                && Objects.equals(result, that.result)
                && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, measurementType, operation, input, result, timestamp);
    }

    @Override
    public String toString() {
        return "QuantityMeasurementEntity{" +
                "id=" + id +
                ", measurementType='" + measurementType + '\'' +
                ", operation='" + operation + '\'' +
                ", input='" + input + '\'' +
                ", result='" + result + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
