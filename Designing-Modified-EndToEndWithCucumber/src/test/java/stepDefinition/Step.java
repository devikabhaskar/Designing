package stepDefinition;

import apiEngine.responses.Book;
import apiEngine.responses.Books;
import apiEngine.responses.Token;
import org.testng.Assert;
import apiEngine.requests.AddBookRequest;
import apiEngine.requests.Authorization;
import apiEngine.requests.ISBN;
import apiEngine.requests.RemoveBookRequest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import apiEngine.EndPoints;
import apiEngine.IRestResponse;
import apiEngine.responses.UserAccount;

public class Step {

	private static final String USER_ID = "55708b1a-69d6-4792-85c9-3c580bd36bdc";

	private static Token tokenResponse;
	private static Book bookID;
	private static Response response;
	private static IRestResponse<UserAccount> userAccountResponse;

	@Given("I am Authorized User")
	public void verifyAuthorisedUser() {

		Authorization credentials = new Authorization("API_Class", "Apiclass@2021");
		tokenResponse = EndPoints.authenitcateUser(credentials).getBody();
	}

	@When("A list of books is available")
	public void checkListOfBooksAvailable() {
		IRestResponse<Books> bookResponse = EndPoints.getBooks();		
		Assert.assertEquals(bookResponse.getStatusCode(), 200);		
		bookID = bookResponse.getBody().books.get(1);// response
	}

	@When("I assign a book to myself")
	public void assignBook() {
		ISBN isbn = new ISBN(bookID.isbn);// request
		AddBookRequest addBookDetails = new AddBookRequest(USER_ID, isbn);
		userAccountResponse = EndPoints.assignBook(addBookDetails, tokenResponse.token);
		Assert.assertEquals(userAccountResponse.getStatusCode(), 201);
	}

	@Then("I remove the book")
	public void removeBook() {
		RemoveBookRequest removeBookRequest = new RemoveBookRequest(USER_ID, bookID.isbn);
		response = EndPoints.removeBook(removeBookRequest, tokenResponse.token);
		Assert.assertEquals(response.getStatusCode(), 204);
	}

	@Then("I confirm the book is removed")
	public void confirmBookRemoved() {
		RemoveBookRequest removeBookRequest = new RemoveBookRequest(USER_ID, bookID.isbn);
		response = EndPoints.confirmBookRemoved(removeBookRequest, tokenResponse.token);		
		Assert.assertEquals(response.getStatusCode(), 400);
		Assert.assertEquals(response.getBody().path("message"), "ISBN supplied is not available in User's Collection!");
	}
	

}
