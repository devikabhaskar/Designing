package apiEngine;

import javax.management.RuntimeErrorException;
import apiEngine.requests.AddBookRequest;
import apiEngine.requests.Authorization;
import apiEngine.requests.RemoveBookRequest;
import apiEngine.responses.Books;
import apiEngine.responses.Token;
import apiEngine.responses.UserAccount;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EndPoints {

	private static final String BASE_URL = "https://demoqa.com";
//	private final RequestSpecification request;

	public static IRestResponse<Token> authenitcateUser(Authorization authRequest) {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		Response response = request.body(authRequest).request(Method.POST, Route.generateToken());
		return new RestResponse(Token.class, response);
	}

	public static IRestResponse<Books> getBooks() {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		Response response = request.request(Method.GET, Route.books());
		return new RestResponse(Books.class, response);

	}

	public static IRestResponse<UserAccount> assignBook(AddBookRequest addBooksRequest, String token) {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		Response response = request.body(addBooksRequest).request(Method.POST, Route.books());
		return new RestResponse(UserAccount.class, response);
	}

	public static Response removeBook(RemoveBookRequest removeBookRequest, String token) {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		return request.body(removeBookRequest).request(Method.DELETE, Route.book());
		  
	}

	public static Response confirmBookRemoved(RemoveBookRequest removeBookRequest, String token) {
		RestAssured.baseURI = BASE_URL;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		return request.body(removeBookRequest).request(Method.DELETE, Route.book());
	}
}
