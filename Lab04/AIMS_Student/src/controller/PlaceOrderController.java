package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;
import utils.Configs;

/**
 * This class controls the flow of place order usecase in our AIMS project
 * 
 * @author nguyenlm
 */
public class PlaceOrderController extends BaseController {

	/**
	 * Just for logging purpose
	 */
	private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());

	/**
	 * This method checks the availability of product when user click PlaceOrder
	 * button
	 * 
	 * @throws SQLException
	 */
	public void placeOrder() throws SQLException {
		Cart.getCart().checkAvailabilityOfProduct();
	}

	/**
	 * This method creates the new Order based on the Cart
	 * 
	 * @return Order
	 * @throws SQLException
	 */
	public Order createOrder() throws SQLException {
		Order order = new Order();
		for (Object object : Cart.getCart().getListMedia()) {
			CartMedia cartMedia = (CartMedia) object;
			OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(), cartMedia.getQuantity(), cartMedia.getPrice());
			order.getListOrderMedia().add(orderMedia);
		}
		return order;
	}

	/**
	 * This method creates the new Invoice based on order
	 * 
	 * @param order
	 * @return Invoice
	 */
	public Invoice createInvoice(Order order) {
		return new Invoice(order);
	}

	/**
	 * This method takes responsibility for processing the shipping info from user
	 * 
	 * @param info
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void processDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException {
		LOGGER.info("Process Delivery Info");
		LOGGER.info(info.toString());
		validateDeliveryInfo(info);
	}

	/**
	 * The method validates the info
	 * 
	 * @param info
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException {

	}

	/**
	 * Method validate user's phone number
	 * 
	 * @param phoneNumber: user's phone number
	 * @return boolean represents valid or not
	 */
	public boolean validatePhoneNumber(String phoneNumber) {
		if (phoneNumber.length() != 10) {
			return false;
		}
		if (!phoneNumber.startsWith("0")) {
			return false;
		}
		try {
			Integer.parseInt(phoneNumber);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}

	/**
	 * Method validate name of user
	 * 
	 * @param name: name of user
	 * @return boolean represents valid or not
	 */
	public boolean validateName(String name) {
		if (name == null)
			return false;
		Pattern p = Pattern.compile(Configs.REGEX_VALIDATE_NAME);
		Matcher m = p.matcher(name);
		return m.matches();
	}

	/**
	 * Method validate user's address
	 * 
	 * @param address: user's address
	 * @return boolean represents valid or not
	 */
	public boolean validateAddress(String address) {
		boolean result = false;
		if (address == null)
			return result;
		for (String province : Configs.PROVINCES) {
			if (address.toLowerCase().contains(province.toLowerCase())) {
				return true;
			}
		}
		return result;
	}

	/**
	 * This method calculates the shipping fees of order
	 * 
	 * @param order
	 * @return shippingFee
	 */
	public int calculateShippingFee(Order order) {
		Random rand = new Random();
		int fees = (int) (((rand.nextFloat() * 10) / 100) * order.getAmount());
		LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
		return fees;
	}

}
