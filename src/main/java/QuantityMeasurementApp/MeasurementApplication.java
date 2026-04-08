package QuantityMeasurementApp;

import com.quantity.measurement.enums.LengthUnit;
import com.quantity.measurement.model.QuantityLength;

public class MeasurementApplication {

	public static void main(String[] args) {
		demonstrateLengthConversion();
		demonstrateLengthEquality();
		demonstrateLengthComparison();
	}

	public static void demonstrateLengthConversion() {
		QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARD);
		QuantityLength inchesObj = yard.convertTo(LengthUnit.INCH);
		System.out.println("Conversion: " + yard + " = " + inchesObj.getValue() + " " + inchesObj.getUnit().name());
	}

	public static void demonstrateLengthEquality() {
		QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCH);
		System.out.println("Equality: " + q1 + " and " + q2 + " Equal? " + q1.equals(q2));
	}

	public static void demonstrateLengthComparison() {
		QuantityLength yard = new QuantityLength(1.0, LengthUnit.YARD);
		QuantityLength feet = new QuantityLength(3.0, LengthUnit.FEET);
		System.out.println("Comparison: " + yard + " vs " + feet + " Equal? " + yard.equals(feet));
	}
}
