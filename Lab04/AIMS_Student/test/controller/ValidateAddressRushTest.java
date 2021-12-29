package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidateAddressRushTest {

	private PlaceRushOrderController placeRushOrderController;
	@BeforeEach
	void setUp() throws Exception {
		placeRushOrderController = new PlaceRushOrderController();
	
	}
	
	@ParameterizedTest
	@CsvSource({
		"Gia Lâm Hà Nội, true",
		"Hà Tĩnh, false",
		"hà nộii, true",
		"Hai bà TRưng HÀ nội, true",
		"London, false",
		"New York, false"
	})
	void test(String address, boolean expected) {
		boolean isValided = placeRushOrderController.validateAddress(address);
		assertEquals(expected, isValided);
	}

}
