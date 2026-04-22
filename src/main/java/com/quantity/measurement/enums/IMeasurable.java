package com.quantity.measurement.enums;

public interface IMeasurable {
    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);
    double getConversionFactor();
    String getUnitName();
}

