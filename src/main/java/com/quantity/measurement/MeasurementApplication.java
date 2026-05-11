package com.quantity.measurement;

import com.quantity.measurement.controller.QuantityMeasurementController;
import com.quantity.measurement.dto.QuantityDTO;
import com.quantity.measurement.dto.QuantityDTO.Unit;
import com.quantity.measurement.dto.QuantityDTO.MeasurementType;
import com.quantity.measurement.repository.QuantityMeasurementRepository;
import com.quantity.measurement.repository.QuantityMeasurementCacheRepository;
import com.quantity.measurement.repository.QuantityMeasurementRepository;
import com.quantity.measurement.service.QuantityMeasurementService;
import com.quantity.measurement.service.QuantityMeasurementService;
import com.quantity.measurement.serviceImpl.QuantityMeasurementServiceImpl;

/**
 * UC15-compliant MeasurementApplication.
 * Entry point wiring Controller, Service, and Repository layers.
 */
public class MeasurementApplication {

	public static void main(String[] args) {
		QuantityMeasurementController controller = createController();

		// Equality check: 1 FEET == 12 INCH
		QuantityDTO a = new QuantityDTO(1.0, Unit.FEET, MeasurementType.LENGTH);
		QuantityDTO b = new QuantityDTO(12.0, Unit.INCH, MeasurementType.LENGTH);
		controller.displayResult(controller.performCompare(a, b));

		// Conversion: 1 KILOGRAM -> GRAM
		QuantityDTO weight = new QuantityDTO(1.0, Unit.KILOGRAM, MeasurementType.WEIGHT);
		controller.displayResult(controller.performConvert(weight, Unit.GRAM));

		// Addition: 1 FEET + 12 INCH -> FEET
		controller.displayResult(controller.performAdd(a, b, Unit.FEET));
	}

	private static QuantityMeasurementController createController() {
		QuantityMeasurementRepository repo = QuantityMeasurementCacheRepository.getInstance();
		QuantityMeasurementService service = new QuantityMeasurementServiceImpl(repo);
		return new QuantityMeasurementController(service);
	}
}
