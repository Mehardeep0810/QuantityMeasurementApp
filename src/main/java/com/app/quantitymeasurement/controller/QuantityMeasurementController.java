package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityDTO.Unit;
import com.app.quantitymeasurement.service.QuantityMeasurementService;

public class QuantityMeasurementController {
    private final QuantityMeasurementService service;

    public QuantityMeasurementController(QuantityMeasurementService service) {
        this.service = service;
    }

    public QuantityDTO performCompare(QuantityDTO a, QuantityDTO b) { return service.compare(a, b); }
    public QuantityDTO performAdd(QuantityDTO a, QuantityDTO b, Unit targetUnit) { return service.add(a, b, targetUnit); }
    public QuantityDTO performSubtract(QuantityDTO a, QuantityDTO b, Unit targetUnit) { return service.subtract(a, b, targetUnit); }
    public QuantityDTO performDivide(QuantityDTO a, QuantityDTO b) { return service.divide(a, b); }
    public QuantityDTO performConvert(QuantityDTO source, Unit targetUnit) { return service.convert(source, targetUnit); }

    public void displayResult(QuantityDTO dto) {
        if (dto == null) System.out.println("Null DTO");
        else System.out.println(dto);
    }
}