package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityDTO.Unit;

public interface QuantityMeasurementService {
    QuantityDTO compare(QuantityDTO a, QuantityDTO b);
    QuantityDTO add(QuantityDTO a, QuantityDTO b, Unit targetUnit);
    QuantityDTO subtract(QuantityDTO a, QuantityDTO b, Unit targetUnit);
    QuantityDTO convert(QuantityDTO source, Unit targetUnit);
    QuantityDTO divide(QuantityDTO a, QuantityDTO b);
}