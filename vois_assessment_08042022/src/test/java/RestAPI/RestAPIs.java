package RestAPI;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class RestAPIs {

	static int userID = 1;

	@BeforeClass
	public static void setup() {
		userID = (int) Math.floor(Math.random() * (10 - 1 + 1) + 1);
		RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
	}

	@Test
	public void printUserEmailFromID() {
		System.out.println("User Email is: "
				+ given().when().get("users/" + userID).then().extract().response().jsonPath().getString("email"));
	}

	@Test
	public static void getPostsByUserID() {
		Response response = given().when().get("posts?userId=" + userID).then().extract().response();
		assertEquals(response.statusCode(), 200);

		List<Integer> userPosts = response.jsonPath().getList("id");
		for (int post : userPosts) {
			int mini = 0, max = 101;
			assertTrue(post > mini);
			assertTrue(post < max);
		}
	}

	@Test
	public void makePostWithUserID() {
		Response response = given().header("Content-type", "application/json").queryParam("userId", userID)
				.queryParam("title", "test API").queryParam("body", "Post API task for VOIS").when().post("/posts")
				.then().extract().response();
		assertEquals(response.statusCode(), 201);
		assertEquals(response.jsonPath().getString("id"), "101");
	}

}