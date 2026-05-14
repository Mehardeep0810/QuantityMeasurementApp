package com.app.quantitymeasurement.dto;

public class QuantityDTO {
    public enum MeasurementType {
        LENGTH, WEIGHT, VOLUME, TEMPERATURE
    }

    public enum Unit {
        // Length
        FEET, INCH, YARD, CENTIMETER, METER,
        // Weight
        KILOGRAM, GRAM, TONNE, POUND,
        // Volume
        LITRE, MILLILITRE, GALLON,
        // Temperature
        CELSIUS, FAHRENHEIT, KELVIN
    }

    private final double value;
    private final Unit unit;
    private final MeasurementType type;
    private final boolean error;
    private final String errorMessage;

    public QuantityDTO(double value, Unit unit, MeasurementType type) {
        this.value = value;
        this.unit = unit;
        this.type = type;
        this.error = false;
        this.errorMessage = null;
    }

    private QuantityDTO(String errorMessage, MeasurementType type) {
        this.value = Double.NaN;
        this.unit = null;
        this.type = type;
        this.error = true;
        this.errorMessage = errorMessage;
    }

    public static QuantityDTO error(String message, MeasurementType type) {
        return new QuantityDTO(message, type);
    }

    public double getValue() { return value; }
    public Unit getUnit() { return unit; }
    public MeasurementType getType() { return type; }
    public boolean hasError() { return error; }
    public String getErrorMessage() { return errorMessage; }

    @Override
    public String toString() {
        return error ? "Error: " + errorMessage : value + " " + unit;
    }
}
