package com.quantity.measurement.entity;

import java.io.Serializable;
import com.quantity.measurement.dto.QuantityDTO;

/**
 * UC15-compliant QuantityMeasurementEntity.
 * Immutable persistence object capturing operation details.
 * Implements Serializable for repository persistence.
 */
public final class QuantityMeasurementEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private final QuantityDTO.MeasurementType type;
    private final String operation;
    private final Double operandA;
    private final String unitA;
    private final Double operandB;
    private final String unitB;
    private final Double result;
    private final String resultUnit;
    private final boolean error;
    private final String errorMessage;
    private final long timestamp;

    // Binary operation constructor
    public QuantityMeasurementEntity(QuantityDTO.MeasurementType type, String operation,
                                     double operandA, String unitA,
                                     double operandB, String unitB,
                                     double result, String resultUnit) {
        this.type = type;
        this.operation = operation;
        this.operandA = operandA;
        this.unitA = unitA;
        this.operandB = operandB;
        this.unitB = unitB;
        this.result = result;
        this.resultUnit = resultUnit;
        this.error = false;
        this.errorMessage = null;
        this.timestamp = System.currentTimeMillis();
    }

    // Single operand constructor (convert/divide)
    public QuantityMeasurementEntity(QuantityDTO.MeasurementType type, String operation,
                                     double operandA, String unitA,
                                     double result, String resultUnit) {
        this(type, operation, operandA, unitA, Double.NaN, null, result, resultUnit);
    }

    // Error constructor
    public QuantityMeasurementEntity(QuantityDTO.MeasurementType type, String operation, String errorMessage) {
        this.type = type;
        this.operation = operation;
        this.operandA = Double.NaN;
        this.unitA = null;
        this.operandB = Double.NaN;
        this.unitB = null;
        this.result = Double.NaN;
        this.resultUnit = null;
        this.error = true;
        this.errorMessage = errorMessage;
        this.timestamp = System.currentTimeMillis();
    }

    public boolean hasError() { return error; }
    public String getErrorMessage() { return errorMessage; }
    public String getOperation() { return operation; }
    public QuantityDTO.MeasurementType getType() { return type; }
    public Double getResult() { return result; }
    public String getResultUnit() { return resultUnit; }
    public long getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        if (error) return String.format("[%s] %s ERROR: %s", type, operation, errorMessage);
        if (operandB != null && !operandB.isNaN()) {
            return String.format("[%s] %s: %s %s and %s %s => %s %s",
                    type, operation, operandA, unitA, operandB, unitB, result, resultUnit);
        } else {
            return String.format("[%s] %s: %s %s => %s %s",
                    type, operation, operandA, unitA, result, resultUnit);
        }
    }
}
