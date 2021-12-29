package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidatePhoneNumberTest {
	
	private PlaceOrderController placeOrderController;

	@BeforeEach
	void setUp() throws Exception {
		placeOrderController = new PlaceOrderController();
	}

	@ParameterizedTest
	@CsvSource({
		"0392683826, true",
		"11223344, false",
		"anh27022000, false",
		"88886666, false"
	})
	void test(String phone, boolean expected) {
		boolean isValided = placeOrderController.validatePhoneNumber(phone);
		assertEquals(expected, isValided);
	}
}
