package com.quantity.measurement.controller;

import com.quantity.measurement.dto.QuantityDTO;
import com.quantity.measurement.service.QuantityMeasurementService;

/**
 * UC15-compliant Controller.
 * Acts as a Facade over the service layer.
 * Provides performXXX methods for REST readiness.
 */
public class QuantityMeasurementController {
    private final QuantityMeasurementService service;

    public QuantityMeasurementController(QuantityMeasurementService service) {
        this.service = service;
    }

    public QuantityDTO performCompare(QuantityDTO a, QuantityDTO b) {
        return service.compare(a, b);
    }

    public QuantityDTO performConvert(QuantityDTO input, QuantityDTO.Unit targetUnit) {
        return service.convert(input, targetUnit);
    }

    public QuantityDTO performAdd(QuantityDTO a, QuantityDTO b, QuantityDTO.Unit targetUnit) {
        return service.add(a, b, targetUnit);
    }

    public QuantityDTO performSubtract(QuantityDTO a, QuantityDTO b, QuantityDTO.Unit targetUnit) {
        return service.subtract(a, b, targetUnit);
    }

    public QuantityDTO performDivide(QuantityDTO a, QuantityDTO b) {
        return service.divide(a, b);
    }

    /**
     * Formats and displays results. No business logic here.
     */
    public void displayResult(QuantityDTO dto) {
        if (dto == null) {
            System.out.println("No result");
        } else if (dto.hasError()) {
            System.out.println("Error: " + dto.getErrorMessage());
        } else {
            System.out.println("Result: " + dto.getValue() + " " + dto.getUnit());
        }
    }
}
