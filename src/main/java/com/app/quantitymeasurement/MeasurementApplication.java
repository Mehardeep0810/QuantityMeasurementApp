package com.app.quantitymeasurement;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityDTO.Unit;
import com.app.quantitymeasurement.dto.QuantityDTO.MeasurementType;
import com.app.quantitymeasurement.repoImpl.QuantityMeasurementCacheRepository;
import com.quantity.measurement.repoImpl.QuantityMeasurementDatabaseRepository;
import com.quantity.measurement.repository.QuantityMeasurementRepository;
import com.quantity.measurement.service.QuantityMeasurementService;
import com.quantity.measurement.serviceImpl.QuantityMeasurementServiceImpl;
import com.quantity.measurement.util.ConnectionPool;

public class MeasurementApplication {
	public static void main(String[] args) {
		QuantityMeasurementController controller = createController();

		QuantityDTO a = new QuantityDTO(1.0, Unit.FEET, MeasurementType.LENGTH);
		QuantityDTO b = new QuantityDTO(12.0, Unit.INCH, MeasurementType.LENGTH);

		controller.displayResult(controller.performCompare(a, b));

		QuantityDTO weight = new QuantityDTO(1.0, Unit.KILOGRAM, MeasurementType.WEIGHT);
		controller.displayResult(controller.performConvert(weight, Unit.GRAM));

		controller.displayResult(controller.performAdd(a, b, Unit.FEET));
		controller.displayResult(controller.performSubtract(a, b, Unit.FEET));
		controller.displayResult(controller.performDivide(a, b));
	}

	private static QuantityMeasurementController createController() {
		String mode = System.getProperty("repo.mode", "CACHE"); // default to cache
		QuantityMeasurementRepository repo;

		if ("DB".equalsIgnoreCase(mode)) {
			repo = new QuantityMeasurementDatabaseRepository(
					new ConnectionPool("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "", 5)
			);
			((QuantityMeasurementDatabaseRepository) repo).initializeSchema();
		} else {
			repo = QuantityMeasurementCacheRepository.getInstance();
		}

		QuantityMeasurementService service = new QuantityMeasurementServiceImpl(repo);
		return new QuantityMeasurementController(service);
	}
}