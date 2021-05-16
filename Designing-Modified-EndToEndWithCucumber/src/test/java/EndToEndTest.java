
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.http.Method;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EndToEndTest {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		// Given I am an authorised user -> POST /Account​/v1​/GenerateToken
		RestAssured.baseURI = "https://demoqa.com";

		RequestSpecification request = RestAssured.given();

		FileInputStream fis = new FileInputStream(new File(
				"C:\\Users\\hp\\eclipse-workspaceAPI_DesigningModified\\Designing-Modified-EndToEndWithCucumber\\src\\test\\java\\Data\\requestBody.json"));

//		String requestBody = "{\r\n" + "  \"userName\": \"API_Class\",\r\n" + "  \"password\": \"Apiclass@2021\"\r\n"
//				+ "}";
		request.header("Content-Type", "application/json");
		Response tokenResponse = request.body(IOUtils.toString(fis, "UTF-8")).request(Method.POST,
				"/Account/v1/GenerateToken");

		String tokenGenerated = tokenResponse.getBody().path("token");
		System.out.println("Generated Token -- " + tokenGenerated);

		// And I assign a book to myself - >POST /BookStore/v1/Books

		request.header("Authorization", "Bearer " + tokenGenerated).header("Content-Type", "application/json");

		FileInputStream fisAddBookDetails = new FileInputStream(new File(
				"C:\\Users\\hp\\eclipse-workspaceAPI_DesigningModified\\Designing-Modified-EndToEndWithCucumber\\src\\test\\java\\Data\\addBookDetails.json"));

//		String addBookDetails = "{\r\n" + "  \"userId\": \"55708b1a-69d6-4792-85c9-3c580bd36bdc\",\r\n"
//				+ "  \"collectionOfIsbns\": [\r\n" + "    {\r\n" + "      \"isbn\": \"9781449325862\"\r\n" + "    }\r\n"
//				+ "  ]\r\n" + "}";

		Response addBooksResponse = request.body(IOUtils.toString(fisAddBookDetails, "UTF-8"))
				.post("/BookStore/v1/Books");

		Assert.assertEquals(addBooksResponse.getStatusCode(), 201);

		addBooksResponse.prettyPrint();

		// Then I remove the book - > DELETE /BookStore/v1/Book

		FileInputStream fisDeleteBody = new FileInputStream(new File(
				"C:\\Users\\hp\\eclipse-workspaceAPI_DesigningModified\\Designing-Modified-EndToEndWithCucumber\\src\\test\\java\\Data\\deleteBody.json"));
//		String deleteBody = "{\r\n" + "  \"isbn\": \"9781449325862\",\r\n"
//				+ "  \"userId\": \"55708b1a-69d6-4792-85c9-3c580bd36bdc\"\r\n" + "}";

		request.header("Content-Type", "application/json");
		Response responseDelete = request.body(IOUtils.toString(fisDeleteBody, "UTF-8")).delete("/BookStore/v1/Book");
		System.out.println("Delete Request  :" + responseDelete.getStatusCode());

		// And I confirm the book is removed - >DELETE /BookStore/v1/Book

		Response responseDeleteConfirm = request.body(IOUtils.toString(fisDeleteBody, "UTF-8")).delete("/BookStore/v1/Book");

		Assert.assertEquals(responseDeleteConfirm.getStatusCode(), 400);

		Assert.assertEquals(responseDeleteConfirm.getBody().path("message"),
				"ISBN supplied is not available in User's Collection!");

	}

}
