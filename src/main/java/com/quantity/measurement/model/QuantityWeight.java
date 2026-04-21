package com.quantity.measurement.model;

import com.quantity.measurement.enums.WeightUnit;

public class QuantityWeight {

    private static final double EPSILON = 1e-6;

    private final double value;
    private final WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    // Convert
    public QuantityWeight convert(WeightUnit targetUnit) {
        double base = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(base);
        return new QuantityWeight(converted, targetUnit);
    }

    // Add (implicit unit)
    public QuantityWeight add(QuantityWeight other) {
        return add(other, this.unit);
    }

    // Add (explicit unit)
    public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {
        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException("Invalid operands");
        }

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        double sum = base1 + base2;

        return new QuantityWeight(
                targetUnit.convertFromBaseUnit(sum),
                targetUnit
        );
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        // 🔥 UC9 IMPORTANT: Category safety
        if (obj == null || this.getClass() != obj.getClass()) return false;

        QuantityWeight other = (QuantityWeight) obj;

        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        return Math.abs(base1 - base2) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(unit.convertToBaseUnit(value));
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}
