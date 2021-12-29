package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidateNameTest {

	private PlaceOrderController placeOrderController;
	@BeforeEach
	void setUp() throws Exception {
		placeOrderController = new PlaceOrderController();
	}

	@ParameterizedTest
	@CsvSource({
		"Nguyễn Năng Đức Anh, true",
		"anh27022000, false",
		"27022000, false"
	})
	void test(String name, boolean expected) {
		boolean isValided = placeOrderController.validateName(name);
		assertEquals(isValided, expected);
	}
}
