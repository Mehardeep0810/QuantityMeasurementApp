package QuantityMeasurementApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.quantity.measurement.enums.LengthUnit;
import com.quantity.measurement.model.QuantityLength;

@SpringBootApplication
public class MeasurementApplication {
	public static void main(String[] args) {
		SpringApplication.run(MeasurementApplication.class, args);

		QuantityLength length1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength length2 = new QuantityLength(12.0, LengthUnit.INCHES);

		System.out.println("Comparing " + length1 + " and " + length2);
		System.out.println("Are they equal? " + length1.equals(length2));
	}
}
