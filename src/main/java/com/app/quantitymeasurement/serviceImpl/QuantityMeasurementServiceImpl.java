package com.app.quantitymeasurement.serviceImpl;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityDTO.Unit;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import com.app.quantitymeasurement.service.QuantityMeasurementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuantityMeasurementServiceImpl implements QuantityMeasurementService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuantityMeasurementServiceImpl.class);
    private final QuantityMeasurementRepository repo;

    public QuantityMeasurementServiceImpl(QuantityMeasurementRepository repo) {
        this.repo = repo;
    }

    @Override
    public QuantityDTO compare(QuantityDTO a, QuantityDTO b) {
        LOGGER.debug("Comparing {} and {}", a, b);
        double valueA = normalize(a);
        double valueB = normalize(b);

        boolean equal = Math.abs(valueA - valueB) < 1e-6;
        LOGGER.info("Comparison result: {} == {} -> {}", a, b, equal);

        repo.save(new QuantityMeasurementEntity(
                a.getType().name(),
                "COMPARE",
                a.getValue() + " " + a.getUnit().name() + "==" + b.getValue() + " " + b.getUnit().name(),
                String.valueOf(equal)
        ));

        return new QuantityDTO(equal ? 1.0 : 0.0, a.getUnit(), a.getType());
    }

    @Override
    public QuantityDTO add(QuantityDTO a, QuantityDTO b, Unit targetUnit) {
        LOGGER.debug("Adding {} and {} into {}", a, b, targetUnit);
        if (a.getType() == QuantityDTO.MeasurementType.TEMPERATURE ||
                b.getType() == QuantityDTO.MeasurementType.TEMPERATURE) {
            LOGGER.error("Attempted to add temperature values, which is unsupported");
            throw new UnsupportedOperationException("Arithmetic operations not supported for Temperature");
        }

        double sum = normalize(a) + normalize(b);
        double converted = convertToTarget(sum, targetUnit, a.getType());
        LOGGER.info("Addition result: {} + {} = {} {}", a, b, converted, targetUnit);

        repo.save(new QuantityMeasurementEntity(
                a.getType().name(),
                "ADD",
                a.getValue() + "+" + b.getValue(),
                converted + " " + targetUnit.name()
        ));

        return new QuantityDTO(converted, targetUnit, a.getType());
    }

    @Override
    public QuantityDTO subtract(QuantityDTO a, QuantityDTO b, Unit targetUnit) {
        LOGGER.debug("Subtracting {} from {} into {}", b, a, targetUnit);
        if (a.getType() == QuantityDTO.MeasurementType.TEMPERATURE ||
                b.getType() == QuantityDTO.MeasurementType.TEMPERATURE) {
            LOGGER.error("Attempted to subtract temperature values, which is unsupported");
            throw new UnsupportedOperationException("Arithmetic operations not supported for Temperature");
        }

        double diff = normalize(a) - normalize(b);
        double converted = convertToTarget(diff, targetUnit, a.getType());
        LOGGER.info("Subtraction result: {} - {} = {} {}", a, b, converted, targetUnit);

        repo.save(new QuantityMeasurementEntity(
                a.getType().name(),
                "SUBTRACT",
                a.getValue() + "-" + b.getValue(),
                converted + " " + targetUnit.name()
        ));

        return new QuantityDTO(converted, targetUnit, a.getType());
    }

    @Override
    public QuantityDTO convert(QuantityDTO source, Unit targetUnit) {
        LOGGER.debug("Converting {} into {}", source, targetUnit);
        double normalized = normalize(source);
        double converted = convertToTarget(normalized, targetUnit, source.getType());
        LOGGER.info("Conversion result: {} -> {} {}", source, converted, targetUnit);

        repo.save(new QuantityMeasurementEntity(
                source.getType().name(),
                "CONVERT",
                source.getValue() + " " + source.getUnit().name(),
                converted + " " + targetUnit.name()
        ));

        return new QuantityDTO(converted, targetUnit, source.getType());
    }

    @Override
    public QuantityDTO divide(QuantityDTO a, QuantityDTO b) {
        LOGGER.debug("Dividing {} by {}", a, b);
        if (a.getType() == QuantityDTO.MeasurementType.TEMPERATURE ||
                b.getType() == QuantityDTO.MeasurementType.TEMPERATURE) {
            LOGGER.error("Attempted to divide temperature values, which is unsupported");
            throw new UnsupportedOperationException("Arithmetic operations not supported for Temperature");
        }
        if (b.getValue() == 0) {
            LOGGER.error("Division by zero attempted");
            throw new ArithmeticException("Division by zero not allowed");
        }

        double quotient = normalize(a) / normalize(b);
        LOGGER.info("Division result: {} / {} = {}", a, b, quotient);

        repo.save(new QuantityMeasurementEntity(
                a.getType().name(),
                "DIVIDE",
                a.getValue() + "/" + b.getValue(),
                quotient + " " + a.getUnit().name()
        ));

        return new QuantityDTO(quotient, a.getUnit(), a.getType());
    }

    // --- Helpers ---
    private double normalize(QuantityDTO q) {
        switch (q.getType()) {
            case LENGTH:
                switch (q.getUnit()) {
                    case FEET: return q.getValue();
                    case INCH: return q.getValue() / 12.0;
                    case YARD: return q.getValue() * 3.0;
                    case CENTIMETER: return q.getValue() / 30.48;
                }
                break;
            case WEIGHT:
                switch (q.getUnit()) {
                    case KILOGRAM: return q.getValue();
                    case GRAM: return q.getValue() / 1000.0;
                    case POUND: return q.getValue() * 0.453592;
                }
                break;
            case TEMPERATURE:
                switch (q.getUnit()) {
                    case CELSIUS: return q.getValue();
                    case FAHRENHEIT: return (q.getValue() - 32) * 5.0 / 9.0;
                    case KELVIN: return q.getValue() - 273.15;
                }
                break;
        }
        LOGGER.error("Unsupported unit encountered: {}", q.getUnit());
        throw new UnsupportedOperationException("Unsupported unit: " + q.getUnit());
    }

    private double convertToTarget(double baseValue, Unit targetUnit, QuantityDTO.MeasurementType type) {
        switch (type) {
            case LENGTH:
                if (targetUnit == Unit.FEET) return baseValue;
                if (targetUnit == Unit.INCH) return baseValue * 12.0;
                if (targetUnit == Unit.YARD) return baseValue / 3.0;
                if (targetUnit == Unit.CENTIMETER) return baseValue * 30.48;
                break;
            case WEIGHT:
                if (targetUnit == Unit.KILOGRAM) return baseValue;
                if (targetUnit == Unit.GRAM) return baseValue * 1000.0;
                if (targetUnit == Unit.POUND) return baseValue / 0.453592;
                break;
            case TEMPERATURE:
                if (targetUnit == Unit.CELSIUS) return baseValue;
                if (targetUnit == Unit.FAHRENHEIT) return (baseValue * 9.0 / 5.0) + 32;
                if (targetUnit == Unit.KELVIN) return baseValue + 273.15;
                break;
        }
        LOGGER.error("Conversion not supported to {}", targetUnit);
        throw new UnsupportedOperationException("Conversion not supported to " + targetUnit);
    }
}
