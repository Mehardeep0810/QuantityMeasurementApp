package com.quantity.measurement.model;

import com.quantity.measurement.enums.LengthUnit;

public class QuantityLength {

    private static final double EPSILON = 1e-6;

    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        if (unit == null) throw new IllegalArgumentException("Unit shouldn't be null");
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid numeric value!");
        this.value = value;
        this.unit = unit;
    }

    /**
     * Instance conversion: returns a new QuantityLength in target unit (object form).
     * No rounding is applied here so small values are preserved for assertions.
     */
    public QuantityLength convertTo(LengthUnit targetUnit) {
        if (targetUnit == null) throw new IllegalArgumentException("Target unit shouldn't be null");
        double convertedValue = convert(this.value, this.unit, targetUnit);
        return new QuantityLength(convertedValue, targetUnit);
    }

    /**
     * Convenience: return numeric value of this instance expressed in targetUnit.
     * No rounding applied.
     */
    public double toValue(LengthUnit targetUnit) {
        return convert(this.value, this.unit, targetUnit);
    }

    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }

    /**
     * Static conversion core: converts value from sourceUnit to targetUnit.
     * Normalizes to feet as base unit, then converts to target.
     * IMPORTANT: Do not round here — preserve precision for computations and tests.
     */
    public static double convert(double value, LengthUnit sourceUnit, LengthUnit targetUnit) {
        if (sourceUnit == null || targetUnit == null) {
            throw new IllegalArgumentException("Units shouldn't be empty!!");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid numeric value!");
        }

        double valueInFeet = sourceUnit.toFeet(value);
        double result = targetUnit.fromFeet(valueInFeet);

        return result; // no rounding
    }

    /**
     * Addition: returns a new QuantityLength in this instance's unit.
     * Uses full precision (no rounding) so small sums are preserved.
     */
    public QuantityLength add(QuantityLength other) {
        if (other == null) throw new IllegalArgumentException("Other quantity cannot be null");
        if (!Double.isFinite(this.value)) throw new IllegalArgumentException("Invalid numeric value!");

        double thisFeet = this.unit.toFeet(this.value);
        double otherFeet = other.unit.toFeet(other.value);

        double sumFeet = thisFeet + otherFeet;
        double resultInThisUnit = this.unit.fromFeet(sumFeet);

        return new QuantityLength(resultInThisUnit, this.unit);
    }

    public static QuantityLength add(QuantityLength q1, QuantityLength q2) {
        return q1.add(q2);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityLength other = (QuantityLength) obj;
        double thisInFeet = this.unit.toFeet(this.value);
        double otherInFeet = other.unit.toFeet(other.value);

        return Math.abs(thisInFeet - otherInFeet) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(unit.toFeet(value));
    }

    @Override
    public String toString() {
        return value + " " + unit.name();
    }

    /**
     * Optional helper for display: round to 2 decimals for printing only.
     * Use only when you want human-friendly output, not for assertions.
     */
    public static double formatTwoDecimals(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}
