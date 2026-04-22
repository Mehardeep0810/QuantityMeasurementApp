package com.quantity.measurement.enumsimplm;

import com.quantity.measurement.enums.IMeasurable;

public enum LengthUnit implements IMeasurable {

    FEET(1.0),
    INCH(1.0 / 12),
    YARD(3.0),                  // 1 yard = 3 feet
    CENTIMETERS(0.0328084);     // 1 cm = 0.0328084 feet

    private final double toFeetFactor;

    LengthUnit(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        return value * toFeetFactor;
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        if (!Double.isFinite(baseValue)) {
            throw new IllegalArgumentException("Invalid value");
        }
        return baseValue / toFeetFactor;
    }

    @Override
    public double getConversionFactor() {
        return toFeetFactor;
    }

    @Override
    public String getUnitName() {
        return this.name();
    }
}
