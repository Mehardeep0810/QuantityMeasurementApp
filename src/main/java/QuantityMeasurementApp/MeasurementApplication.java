package QuantityMeasurementApp;

import com.quantity.measurement.enums.LengthUnit;
import com.quantity.measurement.model.QuantityLength;

public class MeasurementApplication {
	public static void main(String[] args) {
		QuantityLength q1 = new QuantityLength(1, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(12, LengthUnit.INCH);

		System.out.println("Equality check: " + q1.equals(q2));

		QuantityLength q3 = new QuantityLength(1, LengthUnit.INCH);
		QuantityLength q4 = new QuantityLength(1, LengthUnit.INCH);

		System.out.println("Equality check: " + q3.equals(q4));

		QuantityLength sum = q1.add(q2);
		System.out.println("Addition result (feet): " + sum.toConvert(LengthUnit.FEET));

		QuantityLength sumInYards = q1.add(q2, LengthUnit.YARD);
		System.out.println("Addition result (yards): " + sumInYards.toConvert(LengthUnit.YARD));
	}
}
