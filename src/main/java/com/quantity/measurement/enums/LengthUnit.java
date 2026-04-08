package com.quantity.measurement.enums;

public enum LengthUnit {

    FEET(1.0),              // 1 foot = 1 foot
    INCH(1.0 / 12.0),       // 1 inch = 0.083333... feet
    YARD(3.0),              // 1 yard = 3 feet
    CM(0.0328084);          // 1 cm = 0.0328084 feet

    private final double toFeetFactor;

    LengthUnit(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    /**
     * Convert a value in this unit to feet.
     */
    public double toFeet(double value) {
        return value * toFeetFactor;
    }

    /**
     * Convert a value in feet to this unit.
     */
    public double fromFeet(double valueInFeet) {
        return valueInFeet / toFeetFactor;
    }

    /**
     * Return the conversion factor relative to feet.
     */
    public double getToFeetFactor() {
        return toFeetFactor;
    }
}
