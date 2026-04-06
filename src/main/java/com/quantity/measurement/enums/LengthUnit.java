 package com.quantity.measurement.enums;

public enum LengthUnit {
    FEET(1.0),          // Base unit
    INCHES(1.0 / 12.0); // 12 inches = 1 foot

    private final double toFeetFactor;

    LengthUnit(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    public double toFeet(double value) {
        return value * toFeetFactor;
    }
}
