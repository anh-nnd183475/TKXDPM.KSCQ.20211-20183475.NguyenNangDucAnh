package controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidateTimeTest {
	private PlaceRushOrderController placeRushOrderController;
	@BeforeEach
	void setUp() throws Exception {
		placeRushOrderController = new PlaceRushOrderController();
	
	}

	@ParameterizedTest
	@CsvSource({
		"2021/18/12 00:00:00, false",
		"2021/16/12 00:00:00, false",
		//29/12 là ngày test
		"2021/12/31 00:00:00, true",
		"2022/05/32 00:00:00, false",
		"2021/02/29 00:00:00, false",
		"2021/13/06 00:00:00, false"
	})
	void test(String time, boolean expected) {
		boolean isValided = placeRushOrderController.validateRushShippingTime(time);
		assertEquals(expected, isValided);
	}

}
