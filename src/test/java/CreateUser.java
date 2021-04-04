import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;
import org.testng.Assert;

public class CreateUser {

	@Test
	public void createNewUser() {

		RestAssured.baseURI = "https://bookstore.demoqa.com";

		RequestSpecification postreq = RestAssured.given();
		String requestBody = "{\r\n" + "  \"userName\": \"apiclass1\",\r\n" + "  \"password\": \"Apiclass@2021\"\r\n"
				+ "}";
		postreq.header("Content-Type", "application/json");
		Response response = postreq.body(requestBody).request(Method.POST, "/Account/v1/User");
		
		Assert.assertEquals(response.getStatusCode(), 201);
		
		System.out.println(response.getBody().asString());
		
		String getUserId = response.getBody().path("userID");
		System.out.println(getUserId);
		
//		Assert.assertEquals(response.getBody().path("userId"), true);
		

	}

}
