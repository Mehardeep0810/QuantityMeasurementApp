package QuantityMeasurementApp;

import com.quantity.measurement.enums.LengthUnit;
import com.quantity.measurement.enums.WeightUnit;
import com.quantity.measurement.model.QuantityLength;
import com.quantity.measurement.model.QuantityWeight;

public class MeasurementApplication {

	public static void main(String[] args) {

		// ================= LENGTH =================
		QuantityLength q1 = new QuantityLength(1, LengthUnit.FEET);
		QuantityLength q2 = new QuantityLength(12, LengthUnit.INCH);

		System.out.println(q1 + " and " + q2 + " Equal (" + q1.equals(q2) + ")");

		QuantityLength q3 = new QuantityLength(1, LengthUnit.INCH);
		QuantityLength q4 = new QuantityLength(1, LengthUnit.INCH);

		System.out.println(q3 + " and " + q4 + " Equal (" + q3.equals(q4) + ")");


		// ================= UC9: WEIGHT =================
		QuantityWeight w1 = new QuantityWeight(1, WeightUnit.KILOGRAM);
		QuantityWeight w2 = new QuantityWeight(1000, WeightUnit.GRAM);

		System.out.println("\nWeight:");
		System.out.println(w1 + " and " + w2 + " Equal (" + w1.equals(w2) + ")");

		// Conversion
		System.out.println("1 KG in Gram = " + w1.convert(WeightUnit.GRAM));

		// Addition
		QuantityWeight w3 = new QuantityWeight(500, WeightUnit.GRAM);
		System.out.println("1 KG + 500 G = " + w1.add(w3));

		// Category safety
		System.out.println("1 KG == 1 FEET ? " + w1.equals(q1));
	}
}