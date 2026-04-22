package com.quantity.measurement.enumsimplm;

import com.quantity.measurement.enums.IMeasurable;

public enum WeightUnit implements IMeasurable {

    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(1.0/2.20462);

    private final double toKgFactor;

    WeightUnit(double toKgFactor) {
        this.toKgFactor = toKgFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        return value * toKgFactor;
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        if (!Double.isFinite(baseValue)) {
            throw new IllegalArgumentException("Invalid value");
        }
        return baseValue / toKgFactor;
    }

    @Override
    public double getConversionFactor() {
        return toKgFactor;
    }

    @Override
    public String getUnitName() {
        return this.name();
    }
}
