package com.quantity.measurement.serviceImpl;

import com.quantity.measurement.dto.QuantityDTO;
import com.quantity.measurement.entity.QuantityMeasurementEntity;
import com.quantity.measurement.enums.IMeasurable;
import com.quantity.measurement.enumsimplm.LengthUnit;
import com.quantity.measurement.enumsimplm.WeightUnit;
import com.quantity.measurement.enumsimplm.VolumeUnit;
import com.quantity.measurement.enumsimplm.TemperatureUnit;
import com.quantity.measurement.exception.QuantityMeasurementException;
import com.quantity.measurement.repository.QuantityMeasurementRepository;
import com.quantity.measurement.repository.QuantityMeasurementRepository;
import com.quantity.measurement.service.QuantityMeasurementService;
import com.quantity.measurement.service.QuantityMeasurementService;


/**
 * UC15-compliant QuantityMeasurementServiceImpl.
 * Implements business logic for compare, convert, add, subtract, divide.
 */
public class QuantityMeasurementServiceImpl implements QuantityMeasurementService {

    private final QuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImpl(QuantityMeasurementRepository repository) {
        this.repository = repository;
    }

    private IMeasurable resolveUnit(QuantityDTO.Unit unit) {
        if (unit == null) throw new QuantityMeasurementException("Unit cannot be null");
        switch (unit) {
            // Length
            case FEET: return LengthUnit.FEET;
            case INCH: return LengthUnit.INCH;
            case YARD: return LengthUnit.YARD;
            case CENTIMETER: return LengthUnit.CENTIMETER;
            case METER: return LengthUnit.METER;
            // Weight
            case KILOGRAM: return WeightUnit.KILOGRAM;
            case GRAM: return WeightUnit.GRAM;
            case TONNE: return WeightUnit.TONNE;
            case POUND: return WeightUnit.POUND;
            // Volume
            case LITRE: return VolumeUnit.LITRE;
            case MILLILITRE: return VolumeUnit.MILLILITRE;
            case GALLON: return VolumeUnit.GALLON;
            // Temperature
            case CELSIUS: return TemperatureUnit.CELSIUS;
            case FAHRENHEIT: return TemperatureUnit.FAHRENHEIT;
            case KELVIN: return TemperatureUnit.KELVIN;
            default: throw new QuantityMeasurementException("Unsupported unit: " + unit);
        }
    }

    @Override
    public QuantityDTO compare(QuantityDTO a, QuantityDTO b) {
        try {
            IMeasurable unitA = resolveUnit(a.getUnit());
            IMeasurable unitB = resolveUnit(b.getUnit());

            if (unitA.getMeasurementType() != unitB.getMeasurementType()) {
                throw new QuantityMeasurementException("Cross-category comparison not allowed");
            }

            double baseA = unitA.convertToBaseUnit(a.getValue());
            double baseB = unitB.convertToBaseUnit(b.getValue());
            boolean eq = Math.abs(baseA - baseB) < 1e-9;

            repository.save(new QuantityMeasurementEntity(a.getType(), "COMPARE",
                    a.getValue(), a.getUnit().name(),
                    b.getValue(), b.getUnit().name(),
                    eq ? 1.0 : 0.0, eq ? "EQUAL" : "NOT_EQUAL"));

            return new QuantityDTO(eq ? 1.0 : 0.0, a.getUnit(), a.getType());
        } catch (Exception ex) {
            repository.save(new QuantityMeasurementEntity(a.getType(), "COMPARE", ex.getMessage()));
            return QuantityDTO.error(ex.getMessage(), a.getType());
        }
    }

    @Override
    public QuantityDTO convert(QuantityDTO input, QuantityDTO.Unit targetUnit) {
        try {
            IMeasurable sourceUnit = resolveUnit(input.getUnit());
            IMeasurable target = resolveUnit(targetUnit);

            if (sourceUnit.getMeasurementType() != target.getMeasurementType()) {
                throw new QuantityMeasurementException("Cross-category conversion not allowed");
            }

            double base = sourceUnit.convertToBaseUnit(input.getValue());
            double out = target.convertFromBaseUnit(base);

            repository.save(new QuantityMeasurementEntity(input.getType(), "CONVERT",
                    input.getValue(), input.getUnit().name(),
                    out, targetUnit.name()));

            return new QuantityDTO(out, targetUnit, input.getType());
        } catch (Exception ex) {
            repository.save(new QuantityMeasurementEntity(input.getType(), "CONVERT", ex.getMessage()));
            return QuantityDTO.error(ex.getMessage(), input.getType());
        }
    }

    @Override
    public QuantityDTO add(QuantityDTO a, QuantityDTO b, QuantityDTO.Unit targetUnit) {
        try {
            IMeasurable unitA = resolveUnit(a.getUnit());
            IMeasurable unitB = resolveUnit(b.getUnit());
            IMeasurable target = resolveUnit(targetUnit);

            if (unitA.getMeasurementType() != unitB.getMeasurementType()) {
                throw new QuantityMeasurementException("Cross-category addition not allowed");
            }
            unitA.validateOperationSupport("ADD");
            unitB.validateOperationSupport("ADD");

            double sumBase = unitA.convertToBaseUnit(a.getValue()) + unitB.convertToBaseUnit(b.getValue());
            double out = target.convertFromBaseUnit(sumBase);

            repository.save(new QuantityMeasurementEntity(a.getType(), "ADD",
                    a.getValue(), a.getUnit().name(),
                    b.getValue(), b.getUnit().name(),
                    out, targetUnit.name()));

            return new QuantityDTO(out, targetUnit, a.getType());
        } catch (Exception ex) {
            repository.save(new QuantityMeasurementEntity(a.getType(), "ADD", ex.getMessage()));
            return QuantityDTO.error(ex.getMessage(), a.getType());
        }
    }

    @Override
    public QuantityDTO subtract(QuantityDTO a, QuantityDTO b, QuantityDTO.Unit targetUnit) {
        try {
            IMeasurable unitA = resolveUnit(a.getUnit());
            IMeasurable unitB = resolveUnit(b.getUnit());
            IMeasurable target = resolveUnit(targetUnit);

            if (unitA.getMeasurementType() != unitB.getMeasurementType()) {
                throw new QuantityMeasurementException("Cross-category subtraction not allowed");
            }
            unitA.validateOperationSupport("SUBTRACT");
            unitB.validateOperationSupport("SUBTRACT");

            double diffBase = unitA.convertToBaseUnit(a.getValue()) - unitB.convertToBaseUnit(b.getValue());
            double out = target.convertFromBaseUnit(diffBase);

            repository.save(new QuantityMeasurementEntity(a.getType(), "SUBTRACT",
                    a.getValue(), a.getUnit().name(),
                    b.getValue(), b.getUnit().name(),
                    out, targetUnit.name()));

            return new QuantityDTO(out, targetUnit, a.getType());
        } catch (Exception ex) {
            repository.save(new QuantityMeasurementEntity(a.getType(), "SUBTRACT", ex.getMessage()));
            return QuantityDTO.error(ex.getMessage(), a.getType());
        }
    }

    @Override
    public QuantityDTO divide(QuantityDTO a, QuantityDTO b) {
        try {
            IMeasurable unitA = resolveUnit(a.getUnit());
            IMeasurable unitB = resolveUnit(b.getUnit());

            if (unitA.getMeasurementType() != unitB.getMeasurementType()) {
                throw new QuantityMeasurementException("Cross-category division not allowed");
            }
            if (b.getValue() == 0.0) throw new ArithmeticException("Divide by zero");

            double scalar = unitA.convertToBaseUnit(a.getValue()) / unitB.convertToBaseUnit(b.getValue());

            repository.save(new QuantityMeasurementEntity(a.getType(), "DIVIDE",
                    a.getValue(), a.getUnit().name(),
                    b.getValue(), b.getUnit().name(),
                    scalar, "SCALAR"));

            return new QuantityDTO(scalar, a.getUnit(), a.getType());
        } catch (Exception ex) {
            repository.save(new QuantityMeasurementEntity(a.getType(), "DIVIDE", ex.getMessage()));
            return QuantityDTO.error(ex.getMessage(), a.getType());
        }
    }
}
