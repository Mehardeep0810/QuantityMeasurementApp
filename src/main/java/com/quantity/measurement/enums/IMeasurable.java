package com.quantity.measurement.enums;

import com.quantity.measurement.dto.QuantityDTO;
import com.quantity.measurement.enumsimplm.LengthUnit;
import com.quantity.measurement.enumsimplm.TemperatureUnit;
import com.quantity.measurement.enumsimplm.VolumeUnit;
import com.quantity.measurement.enumsimplm.WeightUnit;

/**
 * UC15-compliant IMeasurable interface.
 * Provides conversion methods, measurement type awareness,
 * unit resolution, and arithmetic support validation.
 */
public interface IMeasurable {
    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);

    /**
     * Each unit must declare its measurement category (Length, Weight, Volume, Temperature).
     */
    QuantityDTO.MeasurementType getMeasurementType();

    /**
     * Resolve a unit instance by name (case-insensitive).
     * Implementations may override for category-specific lookup.
     */
    static IMeasurable getUnitInstance(String name) {
        if (name == null) return null;
        String n = name.trim().toUpperCase();
        try {
            for (LengthUnit u : LengthUnit.values()) if (u.name().equals(n)) return u;
        } catch (Throwable ignored) {}
        try {
            for (WeightUnit u : WeightUnit.values()) if (u.name().equals(n)) return u;
        } catch (Throwable ignored) {}
        try {
            for (VolumeUnit u : VolumeUnit.values()) if (u.name().equals(n)) return u;
        } catch (Throwable ignored) {}
        try {
            for (TemperatureUnit u : TemperatureUnit.values()) if (u.name().equals(n)) return u;
        } catch (Throwable ignored) {}
        return null;
    }

    @FunctionalInterface
    interface SupportsArithmetic {
        boolean isSupported();
    }

    default String getUnitName() {
        return this.getClass().getSimpleName();
    }

    /**
     * By default, arithmetic is supported. Units like Temperature override this.
     */
    default SupportsArithmetic supportsArithmetic() {
        return () -> true;
    }

    /**
     * Validate whether a given operation is supported.
     * Default is no-op; Temperature overrides to throw UnsupportedOperationException.
     */
    default void validateOperationSupport(String operation) {
        // no-op by default
    }
}
