package com.app.quantitymeasurement.serviceImpl;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityDTO.MeasurementType;
import com.app.quantitymeasurement.dto.QuantityDTO.Unit;
import com.app.quantitymeasurement.model.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import com.app.quantitymeasurement.service.QuantityMeasurementService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class QuantityMeasurementServiceImpl implements QuantityMeasurementService {

    private final QuantityMeasurementRepository repo;

    public QuantityMeasurementServiceImpl(QuantityMeasurementRepository repo) {
        this.repo = repo;
    }

    @Override
    public QuantityDTO add(QuantityDTO a, QuantityDTO b, Unit targetUnit) {
        if (a.getType() == MeasurementType.TEMPERATURE) {
            throw new UnsupportedOperationException("Temperature arithmetic not supported");
        }
        double result = convertToTarget(a, targetUnit) + convertToTarget(b, targetUnit);
        QuantityDTO dto = new QuantityDTO(result, targetUnit, a.getType());
        persist("ADD", a, b, dto);
        return dto;
    }

    @Override
    public QuantityDTO subtract(QuantityDTO a, QuantityDTO b, Unit targetUnit) {
        if (a.getType() == MeasurementType.TEMPERATURE) {
            throw new UnsupportedOperationException("Temperature arithmetic not supported");
        }
        double result = convertToTarget(a, targetUnit) - convertToTarget(b, targetUnit);
        QuantityDTO dto = new QuantityDTO(result, targetUnit, a.getType());
        persist("SUBTRACT", a, b, dto);
        return dto;
    }

    @Override
    public QuantityDTO divide(QuantityDTO a, QuantityDTO b) {
        if (a.getType() == MeasurementType.TEMPERATURE) {
            throw new UnsupportedOperationException("Temperature division not supported");
        }
        if (b.getValue() == 0.0) {
            throw new ArithmeticException("Division by zero");
        }
        double valA = convertToTarget(a, a.getUnit());
        double valB = convertToTarget(b, a.getUnit());
        double result = valA / valB;
        QuantityDTO dto = new QuantityDTO(result, a.getUnit(), a.getType());
        persist("DIVIDE", a, b, dto);
        return dto;
    }

    @Override
    public QuantityDTO compare(QuantityDTO a, QuantityDTO b) {
        double valA = convertToTarget(a, b.getUnit());
        double valB = b.getValue();
        double result = (Math.abs(valA - valB) < 1e-6) ? 1.0 : 0.0;
        QuantityDTO dto = new QuantityDTO(result, b.getUnit(), a.getType());
        persist("COMPARE", a, b, dto);
        return dto;
    }

    @Override
    public QuantityDTO convert(QuantityDTO source, Unit targetUnit) {
        double result = convertToTarget(source, targetUnit);
        QuantityDTO dto = new QuantityDTO(result, targetUnit, source.getType());
        persist("CONVERT", source, null, dto);
        return dto;
    }

    private double convertToTarget(QuantityDTO dto, Unit targetUnit) {
        Unit src = dto.getUnit();
        double v = dto.getValue();

        if (src == Unit.FEET) {
            if (targetUnit == Unit.INCH) return v * 12.0;
            if (targetUnit == Unit.YARD) return v / 3.0;
            if (targetUnit == Unit.CENTIMETER) return v * 30.48;
            if (targetUnit == Unit.METER) return v * 0.3048;
            if (targetUnit == Unit.FEET) return v;
        }
        if (src == Unit.INCH) {
            if (targetUnit == Unit.FEET) return v / 12.0;
            if (targetUnit == Unit.CENTIMETER) return v * 2.54;
            if (targetUnit == Unit.METER) return v * 0.0254;
            if (targetUnit == Unit.INCH) return v;
        }
        if (src == Unit.YARD) {
            if (targetUnit == Unit.FEET) return v * 3.0;
            if (targetUnit == Unit.YARD) return v;
            if (targetUnit == Unit.METER) return v * 0.9144;
        }
        if (src == Unit.CENTIMETER) {
            if (targetUnit == Unit.FEET) return v / 30.48;
            if (targetUnit == Unit.METER) return v / 100.0;
            if (targetUnit == Unit.CENTIMETER) return v;
        }
        if (src == Unit.METER) {
            if (targetUnit == Unit.CENTIMETER) return v * 100.0;
            if (targetUnit == Unit.FEET) return v * 3.28084;
            if (targetUnit == Unit.METER) return v;
        }

        if (src == Unit.KILOGRAM) {
            if (targetUnit == Unit.GRAM) return v * 1000.0;
            if (targetUnit == Unit.POUND) return v * 2.20462;
            if (targetUnit == Unit.KILOGRAM) return v;
        }
        if (src == Unit.GRAM) {
            if (targetUnit == Unit.KILOGRAM) return v / 1000.0;
            if (targetUnit == Unit.GRAM) return v;
        }
        if (src == Unit.POUND) {
            if (targetUnit == Unit.KILOGRAM) return v * 0.453592;
            if (targetUnit == Unit.POUND) return v;
        }

        if (src == Unit.CELSIUS) {
            if (targetUnit == Unit.FAHRENHEIT) return (v * 9.0/5.0) + 32.0;
            if (targetUnit == Unit.KELVIN) return v + 273.15;
            if (targetUnit == Unit.CELSIUS) return v;
        }
        if (src == Unit.FAHRENHEIT) {
            if (targetUnit == Unit.CELSIUS) return (v - 32.0) * 5.0/9.0;
            if (targetUnit == Unit.FAHRENHEIT) return v;
        }
        if (src == Unit.KELVIN) {
            if (targetUnit == Unit.CELSIUS) return v - 273.15;
            if (targetUnit == Unit.KELVIN) return v;
        }

        if (src == Unit.LITRE) {
            if (targetUnit == Unit.MILLILITRE) return v * 1000.0;
            if (targetUnit == Unit.LITRE) return v;
        }
        if (src == Unit.MILLILITRE) {
            if (targetUnit == Unit.LITRE) return v / 1000.0;
            if (targetUnit == Unit.MILLILITRE) return v;
        }
        if (src == Unit.GALLON) {
            if (targetUnit == Unit.LITRE) return v * 3.78541;
            if (targetUnit == Unit.GALLON) return v;
        }

        return v;
    }

    private void persist(String operation, QuantityDTO a, QuantityDTO b, QuantityDTO result) {
        try {
            QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
                    result.getType() != null ? result.getType().name() : null,
                    operation,
                    a != null ? a.toString() : "",
                    result != null ? result.toString() : ""
            );
            entity.setTimestamp(LocalDateTime.now());
            repo.save(entity);
        } catch (Exception ignored) {
        }
    }
}
