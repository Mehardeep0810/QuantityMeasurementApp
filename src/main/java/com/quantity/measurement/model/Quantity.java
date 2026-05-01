package com.quantity.measurement.model;

import com.quantity.measurement.enums.IMeasurable;

import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

public class Quantity<U extends IMeasurable> {

    private static final double EPSILON = 1e-6;

    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");
        if (Double.isNaN(value)) throw new IllegalArgumentException("Value cannot be NaN");
        this.value = value;
        this.unit = unit;
    }

    public double getValue() { return value; }
    public U getUnit() { return unit; }

    public Quantity<U> convertTo(U targetUnit) {
        if (targetUnit == null) throw new IllegalArgumentException("Target unit cannot be null");
        if (!unit.getClass().equals(targetUnit.getClass()))
            throw new IllegalArgumentException("Cross-category conversion not allowed");
        double base = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(base);
        return new Quantity<>(converted, targetUnit);
    }

    private enum ArithmeticOperation {
        ADD((a, b) -> a + b),
        SUBTRACT((a, b) -> a - b),
        DIVIDE((a, b) -> {
            if (Math.abs(b) < EPSILON) throw new ArithmeticException("Division by zero");
            return a / b;
        });

        private final DoubleBinaryOperator op;
        ArithmeticOperation(DoubleBinaryOperator op) { this.op = op; }
        double compute(double a, double b) { return op.applyAsDouble(a, b); }
    }

    private void validateArithmeticOperands(Quantity<U> other, U targetUnit, boolean targetRequired) {
        if (other == null) throw new IllegalArgumentException("Operand cannot be null");
        if (other.unit == null) throw new IllegalArgumentException("Operand unit cannot be null");
        if (!unit.getClass().equals(other.unit.getClass()))
            throw new IllegalArgumentException("Cross-category operation not allowed");
        if (!Double.isFinite(this.value) || !Double.isFinite(other.value))
            throw new IllegalArgumentException("Values must be finite");
        if (targetRequired && targetUnit == null)
            throw new IllegalArgumentException("Target unit required");
        if (targetUnit != null && !unit.getClass().equals(targetUnit.getClass()))
            throw new IllegalArgumentException("Target unit must be same category");
    }

    private double performBaseArithmetic(Quantity<U> other, ArithmeticOperation op) {
        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);
        return op.compute(base1, base2);
    }

    private double roundToTwoDecimals(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    private double applyRoundingPolicy(double converted) {
        return Math.abs(converted) >= 0.01 ? roundToTwoDecimals(converted) : converted;
    }

    private Quantity<U> operate(Quantity<U> other,
                                ArithmeticOperation op,
                                U targetUnit,
                                boolean targetRequired,
                                boolean applyRounding) {
        validateArithmeticOperands(other, targetUnit, targetRequired);
        double baseResult = performBaseArithmetic(other, op);
        U resultUnit = (targetUnit != null) ? targetUnit : this.unit;
        double converted = resultUnit.convertFromBaseUnit(baseResult);
        double finalValue = applyRounding ? applyRoundingPolicy(converted) : converted;
        return new Quantity<>(finalValue, resultUnit);
    }

    public Quantity<U> add(Quantity<U> other) {
        return operate(other, ArithmeticOperation.ADD, null, false, true);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        return operate(other, ArithmeticOperation.ADD, targetUnit, true, false);
    }

    public Quantity<U> subtract(Quantity<U> other) {
        return operate(other, ArithmeticOperation.SUBTRACT, null, false, true);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        return operate(other, ArithmeticOperation.SUBTRACT, targetUnit, true, false);
    }

    public double divide(Quantity<U> other) {
        validateArithmeticOperands(other, null, false);
        double base1 = unit.convertToBaseUnit(value);
        double base2 = other.unit.convertToBaseUnit(other.value);
        if (Math.abs(base2) < EPSILON) throw new ArithmeticException("Division by zero");
        return base1 / base2;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Quantity<?>)) return false;
        Quantity<?> other = (Quantity<?>) obj;
        if (!unit.getClass().equals(other.unit.getClass())) return false;
        double base1 = unit.convertToBaseUnit(value);
        double base2 = ((IMeasurable) other.unit).convertToBaseUnit(other.value);
        return Math.abs(base1 - base2) < EPSILON;
    }

    @Override
    public int hashCode() {
        double base = unit.convertToBaseUnit(value);
        long bits = Double.doubleToLongBits(roundToTwoDecimals(base));
        return Objects.hash(unit.getClass(), bits);
    }

    @Override
    public String toString() {
        return "Quantity{" + "value=" + value + ", unit=" + unit + '}';
    }
}