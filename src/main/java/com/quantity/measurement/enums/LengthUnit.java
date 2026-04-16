package com.quantity.measurement.enums;

public enum LengthUnit {
    FEET(1.0),
    INCH(1.0 / 12.0),
    YARD(3.0),
    CENTIMETER(0.0328084);

    private final double toFeetFactor;

    LengthUnit(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    public double convertToBaseUnit(double value) {
        return value * toFeetFactor;
    }

    public double convertFromBaseUnit(double valueInFeet) {
        return valueInFeet / toFeetFactor;
    }

    public double convert(double value, LengthUnit target) {
        double baseValue = convertToBaseUnit(value);
        return target.convertFromBaseUnit(baseValue);
    }
}
