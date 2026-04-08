package com.quantity.measurement.enums;

public enum LengthUnit {

    FEET(1.0),              // 1 foot = 1 foot
    INCH(1.0 / 12.0),       // 1 inch = 0.0833 feet
    YARD(3.0),              // 1 yard = 3 feet
    CM(0.0328084);          // 1 cm = 0.0328084 feet (≈ 0.393701 inches)

    private final double toFeetFactor;

    LengthUnit(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    public double toFeet(double value) {
        return value * toFeetFactor;
    }

    public double fromFeet(double value) {
        return value / toFeetFactor;
    }
}
