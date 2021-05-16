package apiEngine;

public class Route {

	private static final String BOOKSTORE = "/BookStore";
	private static final String ACCOUNT = "/Account​";
	private static final String VERSION = "/v1";

	public static String generateToken() {
//		return "/Account​" + VERSION + "/GenerateToken";
		return "/Account/v1/GenerateToken";
	}
	

	public static String books() {
		return "/BookStore" + VERSION + "/Books";
	}

	public static String book() {
		return "/BookStore" + VERSION + "/Book";
	}
}
