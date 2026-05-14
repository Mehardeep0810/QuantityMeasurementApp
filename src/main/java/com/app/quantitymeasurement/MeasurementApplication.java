package com.app.quantitymeasurement;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.dto.QuantityDTO.Unit;
import com.app.quantitymeasurement.dto.QuantityDTO.MeasurementType;
import com.app.quantitymeasurement.repoImpl.QuantityMeasurementCacheRepository;
import com.app.quantitymeasurement.repoImpl.QuantityMeasurementDatabaseRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementRepository;
import com.app.quantitymeasurement.service.QuantityMeasurementService;
import com.app.quantitymeasurement.serviceImpl.QuantityMeasurementServiceImpl;
import com.app.quantitymeasurement.database.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MeasurementApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(MeasurementApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Starting MeasurementApplication...");
		QuantityMeasurementController controller = createController();

		QuantityDTO a = new QuantityDTO(1.0, Unit.FEET, MeasurementType.LENGTH);
		QuantityDTO b = new QuantityDTO(12.0, Unit.INCH, MeasurementType.LENGTH);

		LOGGER.debug("Performing compare operation");
		controller.displayResult(controller.performCompare(a, b));

		QuantityDTO weight = new QuantityDTO(1.0, Unit.KILOGRAM, MeasurementType.WEIGHT);
		LOGGER.debug("Performing convert operation");
		controller.displayResult(controller.performConvert(weight, Unit.GRAM));

		LOGGER.debug("Performing add operation");
		controller.displayResult(controller.performAdd(a, b, Unit.FEET));

		LOGGER.debug("Performing subtract operation");
		controller.displayResult(controller.performSubtract(a, b, Unit.FEET));

		LOGGER.debug("Performing divide operation");
		controller.displayResult(controller.performDivide(a, b));

		LOGGER.info("MeasurementApplication finished successfully.");
	}

	private static QuantityMeasurementController createController() {
		String mode = System.getProperty("repo.mode", "CACHE"); // default to cache
		QuantityMeasurementRepository repo;

		if ("DB".equalsIgnoreCase(mode)) {
			LOGGER.info("Using Database repository mode");
			repo = new QuantityMeasurementDatabaseRepository(
					new ConnectionPool("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "sa", "", 5)
			);
			((QuantityMeasurementDatabaseRepository) repo).initializeSchema();
		} else {
			LOGGER.info("Using Cache repository mode");
			repo = QuantityMeasurementCacheRepository.getInstance();
		}

		QuantityMeasurementService service = new QuantityMeasurementServiceImpl(repo);
		return new QuantityMeasurementController(service);
	}
}
