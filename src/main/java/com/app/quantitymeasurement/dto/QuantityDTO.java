package com.app.quantitymeasurement.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class QuantityDTO {

    public enum MeasurementType {
        LENGTH, WEIGHT, TEMPERATURE, VOLUME
    }

    public enum Unit {
        FEET, INCH, YARD, CENTIMETER, METER,
        KILOGRAM, GRAM, POUND,
        CELSIUS, FAHRENHEIT, KELVIN,
        LITRE, MILLILITRE, GALLON
    }

    private double value;
    private Unit unit;
    private MeasurementType type;

    @JsonCreator
    public QuantityDTO(@JsonProperty("value") double value,
                       @JsonProperty("unit") Unit unit,
                       @JsonProperty("type") MeasurementType type) {
        this.value = value;
        this.unit = unit;
        this.type = type;
    }

    public QuantityDTO() { }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public Unit getUnit() { return unit; }
    public void setUnit(Unit unit) { this.unit = unit; }

    public MeasurementType getType() { return type; }
    public void setType(MeasurementType type) { this.type = type; }

    @Override
    public String toString() {
        return "QuantityDTO{" +
                "value=" + value +
                ", unit=" + unit +
                ", type=" + type +
                '}';
    }
}
