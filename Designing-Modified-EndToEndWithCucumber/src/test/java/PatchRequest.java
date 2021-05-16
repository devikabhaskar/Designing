import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class PatchRequest {
	
	public static String requestBody= "{ \"title\": \"json-server3\"}";

	@Test
	public void postRequest() throws IOException {

		RestAssured.baseURI = "http://localhost:3000";

		RequestSpecification request = RestAssured.given();

		request.header("Content-Type", "application/json");

//		FileInputStream fis = new FileInputStream(new File(
//				"C:\\Users\\hp\\eclipse-workspaceAPI_DesigningModified\\Designing-Modified-EndToEndWithCucumber\\src\\test\\java\\Data\\postRequest.json"));

		Response response = request.body(requestBody).request(Method.PATCH, "/posts/2");

		System.out.println(response.statusCode());
	}

}
