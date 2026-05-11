package com.quantity.measurement.service;

import com.quantity.measurement.dto.QuantityDTO;

/**
 * UC15-compliant service interface.
 * Defines minimal, focused contracts for business logic operations.
 * All inputs/outputs are standardized via QuantityDTO.
 */
public interface QuantityMeasurementService {

    /**
     * Compare two quantities for equality.
     * @param a first quantity
     * @param b second quantity
     * @return QuantityDTO result (value=1.0 for equal, 0.0 for not equal, or error DTO)
     */
    QuantityDTO compare(QuantityDTO a, QuantityDTO b);

    /**
     * Convert a quantity to a target unit.
     * @param input source quantity
     * @param targetUnit target unit (DTO enum)
     * @return converted QuantityDTO or error DTO
     */
    QuantityDTO convert(QuantityDTO input, QuantityDTO.Unit targetUnit);

    /**
     * Add two quantities of the same category.
     * @param a first quantity
     * @param b second quantity
     * @param targetUnit unit for result
     * @return QuantityDTO result or error DTO
     */
    QuantityDTO add(QuantityDTO a, QuantityDTO b, QuantityDTO.Unit targetUnit);

    /**
     * Subtract one quantity from another of the same category.
     * @param a first quantity
     * @param b second quantity
     * @param targetUnit unit for result
     * @return QuantityDTO result or error DTO
     */
    QuantityDTO subtract(QuantityDTO a, QuantityDTO b, QuantityDTO.Unit targetUnit);

    /**
     * Divide one quantity by another of the same category.
     * @param a numerator quantity
     * @param b denominator quantity
     * @return scalar QuantityDTO result or error DTO
     */
    QuantityDTO divide(QuantityDTO a, QuantityDTO b);
}
