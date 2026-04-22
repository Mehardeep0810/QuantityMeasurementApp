package QuantityMeasurementApp;

import com.quantity.measurement.enumsimplm.LengthUnit;
import com.quantity.measurement.enumsimplm.WeightUnit;
import com.quantity.measurement.model.Quantity;
import com.quantity.measurement.enums.IMeasurable;

public class MeasurementApplication {

	public static void main(String[] args) {

		// ================= LENGTH =================
		Quantity<LengthUnit> q1 = new Quantity<>(1, LengthUnit.FEET);
		Quantity<LengthUnit> q2 = new Quantity<>(12, LengthUnit.INCH);
		showResult("Equality", q1, q2);

		Quantity<LengthUnit> q3 = new Quantity<>(1, LengthUnit.INCH);
		Quantity<LengthUnit> q4 = new Quantity<>(1, LengthUnit.INCH);
		showResult("Equality", q3, q4);

		// ================= WEIGHT =================
		Quantity<WeightUnit> w1 = new Quantity<>(1, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> w2 = new Quantity<>(1000, WeightUnit.GRAM);
		showResult("Equality", w1, w2);

		// Conversion
		showConversion(w1, WeightUnit.GRAM);

		// Addition
		Quantity<WeightUnit> w3 = new Quantity<>(500, WeightUnit.GRAM);
		showAddition(w1, w3);

		// Category safety
		System.out.println("1 KG == 1 FEET ? " + w1.equals(q1));
	}

	// Single generic equality method
	private static <U extends IMeasurable> void showResult(String label, Quantity<U> q1, Quantity<U> q2) {
		System.out.println(label + ": " + q1 + " and " + q2 + " Equal (" + q1.equals(q2) + ")");
	}

	// Generic conversion
	private static <U extends IMeasurable> void showConversion(Quantity<U> q, U targetUnit) {
		System.out.println("Conversion: " + q + " = " + q.convertTo(targetUnit));
	}

	// Generic addition
	private static <U extends IMeasurable> void showAddition(Quantity<U> q1, Quantity<U> q2) {
		System.out.println("Addition: " + q1 + " + " + q2 + " = " + q1.add(q2));
	}
}
