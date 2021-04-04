package stepDefinition;
import org.testng.Assert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EndToEndScenario {
	
//	public static String baseURL = "https://demoqa.com";
//	  RestAssured.baseURI = baseURL;
			
	 static {
		 RestAssured.baseURI = "https://demoqa.com";
	 }

	RequestSpecification request = RestAssured.given();
	String tokenGenerated;
	String deleteBody;
	@Given("I am Authorized User")
	public void i_am_authorized_user() {
		
		String requestBody = "{\r\n" + "  \"userName\": \"API_Class\",\r\n" + "  \"password\": \"Apiclass@2021\"\r\n"
				+ "}";
		request.header("Content-Type", "application/json");
		Response tokenResponse = request.body(requestBody).request(Method.POST, "/Account/v1/GenerateToken");

		String tokenGenerated = tokenResponse.getBody().path("token");
		System.out.println("Generated Token -- " + tokenGenerated);
	}

	@When("I assign a book to myself")
	public void i_assign_a_book_to_myself() {
		request.header("Authorization", "Bearer " + tokenGenerated).header("Content-Type", "application/json");

		String addBookDetails = "{\r\n" + "  \"userId\": \"55708b1a-69d6-4792-85c9-3c580bd36bdc\",\r\n"
				+ "  \"collectionOfIsbns\": [\r\n" + "    {\r\n" + "      \"isbn\": \"9781449325862\"\r\n" + "    }\r\n"
				+ "  ]\r\n" + "}";

		Response addBooksResponse = request.body(addBookDetails).post("/BookStore/v1/Books");

		Assert.assertEquals(addBooksResponse.getStatusCode(), 201);

		addBooksResponse.prettyPrint();
	}

	@Then("I remove the book")
	public void i_remove_the_book() {
		String deleteBody = "{\r\n" + "  \"isbn\": \"9781449325862\",\r\n"
				+ "  \"userId\": \"55708b1a-69d6-4792-85c9-3c580bd36bdc\"\r\n" + "}";

		request.header("Content-Type", "application/json");
		Response responseDelete = request.body(deleteBody).delete("/BookStore/v1/Book");
		System.out.println("Delete Request  :" + responseDelete.getStatusCode());
	}

	@Then("I confirm the book is removed")
	public void i_confirm_the_book_is_removed() {
		Response responseDeleteConfirm = request.body(deleteBody).delete("/BookStore/v1/Book");

		Assert.assertEquals(responseDeleteConfirm.getStatusCode(), 400);

		Assert.assertEquals(responseDeleteConfirm.getBody().path("message"),
				"ISBN supplied is not available in User's Collection!");
	}

}
