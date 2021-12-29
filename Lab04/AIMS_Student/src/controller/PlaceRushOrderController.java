package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.order.Order;
import entity.order.OrderMedia;
import utils.Utils;
import utils.Configs;

/**
 * Class for making rush order Date 10/12/2021
 * 
 * @author lenovo
 * @version 1.0
 */
public class PlaceRushOrderController extends PlaceOrderController {

	/**
	 * The method validates the info
	 * @param info
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void validateDeliveryInfo(HashMap<String, String> rushInfo) throws InterruptedException, IOException {
		validateName(rushInfo.get(Configs.USERNAME_KEY));
		validateAddress(rushInfo.get(Configs.ADDRESS_KEY));
		validatePhoneNumber(rushInfo.get(Configs.PHONE_NUMBER_KEY));
		validateRushShippingTime(rushInfo.get(Configs.PLACE_RUSH_ORDER_SHIPPING_TIME));
	}

	/**
	 * Method creates order based on Cart
	 * 
	 * @param info: HashMap contain user's input for rush shipping time
	 * @return order: Corresponding order
	 * @throws SQLException
	 */
	public Order createRushOrder(HashMap<String, String> rushInfo) throws SQLException {
		Order order = new Order();
		for (Object object : Cart.getCart().getListMedia()) {
			CartMedia cartMedia = (CartMedia) object;
			OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(), cartMedia.getQuantity(), cartMedia.getPrice());
			if (cartMedia.getMedia().isRushSupported()) {
				orderMedia.setShippingTime(rushInfo.get(Configs.PLACE_RUSH_ORDER_SHIPPING_TIME));
			}
			order.getListOrderMedia().add(orderMedia);
		}
		return order;
	}
	
	/**
	 * Method validate supported rush shipping address
	 * @param address: user's address for rush shipping
	 * @return boolean represents valid or not
	 */
	public boolean validateAddress(String address) {
		if(address == null) return false;
		if(address.toLowerCase().contains("Hà Nội".toLowerCase())) {
			return true;
		}
		return false;
	}

	/**
	 * Method validate rush shipping time
	 * 
	 * @param time: user's time for rush shipping
	 * @return boolean : represents valid or not
	 */
	public boolean validateRushShippingTime(String time) {
		Date shippingTime;
		DateFormat dateFormat = Utils.DATE_FORMATER;
		dateFormat.setLenient(false);
		
		// check valid time format (yyyy/MM/dd HH:mm:ss)
		try {
			shippingTime = dateFormat.parse(time);
		} catch (ParseException p) {
			return false;
		}
		
		// check shipping time is after today or not
		Date today = new Date();
		if (today.before(shippingTime)) {
			return true;
		}
		return false;
	}

	/**
	 * Method calculate final shipping fee for rush order
	 * 
	 * @param order: order with rush shipping
	 * @return finalFee: amount of shipping fee
	 */
	public int calculateRushShippingFee(Order order) {
		int placeOrderFee = super.calculateShippingFee(order);
		int placeRushOrderFee = placeOrderFee;
		for (Object om : order.getListOrderMedia()) {
			if (((OrderMedia) om).getShippingTime() != null) {
				placeRushOrderFee += Configs.EXTRA_FEE_PER_RUSH_PRODUCT;
			}
		}
		return placeRushOrderFee;
	}
}
