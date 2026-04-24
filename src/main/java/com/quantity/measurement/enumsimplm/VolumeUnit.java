package com.quantity.measurement.enumsimplm;

import com.quantity.measurement.enums.IMeasurable;

public enum VolumeUnit implements IMeasurable {

    LITRE(1.0),          // base unit
    MILLILITRE(0.001),   // 1 mL = 0.001 L
    GALLON(3.78541);     // 1 US gallon = 3.78541 L

    private final double toLitreFactor;

    VolumeUnit(double toLitreFactor) {
        this.toLitreFactor = toLitreFactor;
    }

    @Override
    public double convertToBaseUnit(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        return value * toLitreFactor; // convert to litres
    }

    @Override
    public double convertFromBaseUnit(double baseValue) {
        if (!Double.isFinite(baseValue)) {
            throw new IllegalArgumentException("Invalid value");
        }
        return baseValue / toLitreFactor; // convert from litres
    }

    @Override
    public double getConversionFactor() {
        return toLitreFactor;
    }

    @Override
    public String getUnitName() {
        return name();
    }
}
