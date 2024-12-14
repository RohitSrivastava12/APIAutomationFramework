package api.Endpoints;

import static io.restassured.RestAssured.given;

import api.Payload.User;
import io.restassured.response.Response;

public class UserEndpoints {
	public static Response CreateUser(User payload) {
		Response response = given()
				.header("Content-type", "application/json")
				.header("accept", "application/json")
				.body(payload)
				.when().post(Routes.Post_Url);
		return response;
	}

	public static Response GetUser(String uname) {
		Response response = given()
				.pathParam("username", uname)
				.when().get(Routes.Get_Url);
		return response;
	}

	public static Response UpdateUser(String uname, User payload) {
		Response response = given()
				.pathParam("username", uname)
				.header("Content-type", "application/json")
				.header("accept", "application/json").body(payload)
				.when()
				.put(Routes.Put_Url);
		return response;
	}

	public static Response DeleteUser(String uname) {
		Response response = given()
				.pathParam("username", uname)
				.when()
				.delete(Routes.Delete_Url);
		return response;
	}
}
