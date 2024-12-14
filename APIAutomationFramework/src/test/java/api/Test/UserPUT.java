package api.Test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.Endpoints.UserEndpoints;
import api.Payload.User;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UserPUT {
//POST->PUT->GET
	public Faker data;
	public User payload;
	public JsonPath jsGet;
	public JsonPath jsPost;

	@BeforeClass
	public void SetTestData() {
		data = new Faker();
		payload = new User();
		payload.setId(data.number().numberBetween(10, 100));
		payload.setUsername(data.name().username());
		payload.setFirstName(data.name().firstName());
		payload.setLastName(data.name().lastName());
		payload.setEmail(data.internet().emailAddress());
		payload.setPassword(data.internet().password(5, 10));
		payload.setPhone(data.phoneNumber().cellPhone());
		payload.setUserStatus(data.number().numberBetween(10, 100));
	}

	@Test
	public void UpdatedUser() {
		UserEndpoints.CreateUser(payload);
		payload.setId(data.number().numberBetween(10, 100));
		payload.setFirstName(data.name().firstName());
		Response putResponse = UserEndpoints.UpdateUser(payload.getUsername(), payload);
		putResponse.then().log().all();
		AssertJUnit.assertEquals(putResponse.statusCode(), 200);
		/*
		 * Response getResponse = UserEndpoints.GetUser(payload.getUsername());
		 * getResponse.then().log().all(); jsGet = new JsonPath(getResponse.asString());
		 * AssertJUnit.assertEquals(payload.getId(), jsGet.getInt("id"));
		 * AssertJUnit.assertEquals(payload.getFirstName(), jsGet.get("firstName"));
		 */
	}
}
