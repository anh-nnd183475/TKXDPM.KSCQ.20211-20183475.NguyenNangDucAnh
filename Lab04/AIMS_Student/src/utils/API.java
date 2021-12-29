package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Class for sending requests to server and receiving responses from server Date
 * 06/12/2021
 * 
 * @author lenovo
 * @version 1.0
 */
public class API {
	/**
	 * Attribute for formating the date
	 */
	public static DateFormat DATE_FORMATER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	/**
	 * Attribute for console logging
	 */
	private static Logger LOGGER = Utils.getLogger(Utils.class.getName());

	/**
	 * Method for calling GET Method
	 * 
	 * @param url:   server's url
	 * @param token: user's token
	 * @return response: server's response (string)
	 * @throws Exception
	 */
	public static String get(String url, String token) throws Exception {
		// 1.Set up
		HttpURLConnection conn = setupConnection(url, "GET", token);

		// 2.Read response from the server
		String response = readResponse(conn);

		return response;
	}

	/**
	 * Method for calling POST Method
	 * 
	 * @param url:  server's url
	 * @param data: data to post (JSON)
	 * @return response: response from server (string)
	 * @throws IOException
	 */
	public static String post(String url, String data
//			, String token
	) throws IOException {
		allowMethods("PATCH");
		// 1.Set up
		HttpURLConnection conn = setupConnection(url, "POST", "");

		// 2.Send data
		Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		writer.write(data);
		writer.close();

		// 3.Read response from server
		String response = readResponse(conn);
		return response;
	}

	/**
	 * Method for allowing other protocols (PATCH, PUT,...) (only for Java 11)
	 * 
	 * @deprecated work only with Java version <= 11
	 * @param methods: allowing methods: PATCH, PUT,....
	 */
	private static void allowMethods(String... methods) {
		try {
			Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
			methodsField.setAccessible(true);

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

			String[] oldMethods = (String[]) methodsField.get(null);
			Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
			methodsSet.addAll(Arrays.asList(methods));
			String[] newMethods = methodsSet.toArray(new String[0]);

			methodsField.set(null/* static field */, newMethods);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Method for setting up connection to server
	 * 
	 * @param url:    server's url
	 * @param method: protocols (GET/POST)
	 * @param token:  user's token
	 * @return conn: HttpURLConnection object to the server
	 * @throws IOException
	 */
	private static HttpURLConnection setupConnection(String url, String method, String token) throws IOException {
		LOGGER.info("Request Info:\nRequest URL: " + url + "\n");
		URL line_api_url = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) line_api_url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod(method);
		conn.setRequestProperty("Content-Type", "application/json");
		if (method == "GET") {
			conn.setRequestProperty("Authorization", "Bearer " + token);
		}
		return conn;
	}

	/**
	 * Method for receiving response from the server
	 * 
	 * @param conn: HttpURLConnection object to the server
	 * @return response: server's response
	 * @throws IOException
	 */
	private static String readResponse(HttpURLConnection conn) throws IOException {
		BufferedReader in;
		String inputLine;
		if (conn.getResponseCode() / 100 == 2) {
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder response = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			System.out.println(inputLine);
			response.append(inputLine);
		}
		in.close();
		LOGGER.info("Respone Info: " + response.substring(0, response.length() - 1).toString());
		return response.substring(0, response.length() - 1).toString();
	}

}
