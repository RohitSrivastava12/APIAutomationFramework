package api.Test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.Endpoints.UserEndpoints;
import api.Payload.User;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UserDELETE {
	// POST->DELETE->GET
	public Faker data;
	public User payload;
	public JsonPath js;

	@BeforeClass
	public void SetTestData() {
		data = new Faker();
		payload = new User();
		payload.setId(data.idNumber().hashCode());
		payload.setUsername(data.name().username());
		payload.setFirstName(data.name().firstName());
		payload.setLastName(data.name().lastName());
		payload.setEmail(data.internet().emailAddress());
		payload.setPassword(data.internet().password(5, 10));
		payload.setPhone(data.phoneNumber().cellPhone());
		payload.setUserStatus(data.idNumber().hashCode());
	}

	@Test
	public void DeleteUser() {
		UserEndpoints.CreateUser(payload);
		Response response = UserEndpoints.DeleteUser(payload.getUsername());
		response.then().log().all();
		AssertJUnit.assertEquals(response.statusCode(), 200);
		js = new JsonPath(response.asString());
		int val = js.getInt("code");
		AssertJUnit.assertEquals(val, 200);
		Response getResponse = UserEndpoints.GetUser(payload.getUsername());
		getResponse.then().log().all();
		AssertJUnit.assertEquals(getResponse.statusCode(), 404);
	}

}
