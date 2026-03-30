package QuantityMeasurementApp;

import com.quantity.measurement.enums.LengthUnit;
import com.quantity.measurement.model.QuantityLength;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MeasurementApplicationTests {

	// ===== Feet Tests =====
	@Test
	void testFeetEquality_SameValue() {
		QuantityLength f1 = new QuantityLength(10.0, LengthUnit.FEET);
		QuantityLength f2 = new QuantityLength(10.0, LengthUnit.FEET);

		assertEquals(f1, f2);
	}

	@Test
	void testFeetInEquality_DifferentValue() {
		QuantityLength f1 = new QuantityLength(10.0, LengthUnit.FEET);
		QuantityLength f2 = new QuantityLength(5.0, LengthUnit.FEET);

		assertNotEquals(f1, f2);
	}

	@Test
	void testFeetNullComparison() {
		QuantityLength f1 = new QuantityLength(10.0, LengthUnit.FEET);
		assertFalse(f1.equals(null));
	}

	@Test
	void testFeetDifferentClassComparison() {
		QuantityLength f1 = new QuantityLength(10.0, LengthUnit.FEET);
		assertFalse(f1.equals("Some String"));
	}

	@Test
	void testFeetSameReference() {
		QuantityLength f1 = new QuantityLength(10.0, LengthUnit.FEET);
		assertTrue(f1.equals(f1));
	}

	// ===== Inches Tests =====
	@Test
	void testInchesEquality_SameValue() {
		QuantityLength i1 = new QuantityLength(12.0, LengthUnit.INCHES);
		QuantityLength i2 = new QuantityLength(12.0, LengthUnit.INCHES);

		assertEquals(i1, i2);
	}

	@Test
	void testInchesInEquality_DifferentValue() {
		QuantityLength i1 = new QuantityLength(12.0, LengthUnit.INCHES);
		QuantityLength i2 = new QuantityLength(10.0, LengthUnit.INCHES);

		assertNotEquals(i1, i2);
	}

	@Test
	void testInchesNullComparison() {
		QuantityLength i1 = new QuantityLength(12.0, LengthUnit.INCHES);
		assertFalse(i1.equals(null));
	}

	@Test
	void testInchesDifferentClassComparison() {
		QuantityLength i1 = new QuantityLength(12.0, LengthUnit.INCHES);
		assertFalse(i1.equals("Some String"));
	}

	@Test
	void testInchesSameReference() {
		QuantityLength i1 = new QuantityLength(12.0, LengthUnit.INCHES);
		assertTrue(i1.equals(i1));
	}

	// ===== Cross-Unit Tests =====
	@Test
	void testFeetEqualsInches() {
		QuantityLength f1 = new QuantityLength(1.0, LengthUnit.FEET);
		QuantityLength i1 = new QuantityLength(12.0, LengthUnit.INCHES);

		assertEquals(f1, i1); // 1 foot == 12 inches
	}

	@Test
	void testFeetNotEqualInches() {
		QuantityLength f1 = new QuantityLength(2.0, LengthUnit.FEET);
		QuantityLength i1 = new QuantityLength(12.0, LengthUnit.INCHES);

		assertNotEquals(f1, i1); // 2 feet != 12 inches
	}
}
