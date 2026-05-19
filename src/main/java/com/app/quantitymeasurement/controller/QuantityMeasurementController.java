package com.app.quantitymeasurement.controller;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityDTO.Unit;
import com.app.quantitymeasurement.service.QuantityMeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/measurements")
public class QuantityMeasurementController {

    private final QuantityMeasurementService service;

    @Autowired
    public QuantityMeasurementController(QuantityMeasurementService service) {
        this.service = service;
    }

    @PostMapping("/compare")
    public ResponseEntity<QuantityDTO> compare(@RequestBody List<QuantityDTO> inputs) {
        return ResponseEntity.ok(service.compare(inputs.get(0), inputs.get(1)));
    }

    @PostMapping("/add")
    public ResponseEntity<QuantityDTO> add(@RequestBody List<QuantityDTO> inputs,
                                           @RequestParam Unit targetUnit) {
        return ResponseEntity.ok(service.add(inputs.get(0), inputs.get(1), targetUnit));
    }

    @PostMapping("/subtract")
    public ResponseEntity<QuantityDTO> subtract(@RequestBody List<QuantityDTO> inputs,
                                                @RequestParam Unit targetUnit) {
        return ResponseEntity.ok(service.subtract(inputs.get(0), inputs.get(1), targetUnit));
    }

    @PostMapping("/divide")
    public ResponseEntity<QuantityDTO> divide(@RequestBody List<QuantityDTO> inputs) {
        return ResponseEntity.ok(service.divide(inputs.get(0), inputs.get(1)));
    }

    @PostMapping("/convert")
    public ResponseEntity<QuantityDTO> convert(@RequestBody QuantityDTO source,
                                               @RequestParam Unit targetUnit) {
        return ResponseEntity.ok(service.convert(source, targetUnit));
    }
}
