package api.Test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.*;

import com.github.javafaker.Faker;

import api.Endpoints.UserEndpoints;
import api.Payload.User;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UserPOST {
	public Faker data;
	public User payload;
	public JsonPath jsGet;
	public JsonPath jsPost;

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

	@Test // (dataProvider = "name")
	public void UserIsCreated() {

		Response postresponse = UserEndpoints.CreateUser(payload);
		postresponse.then().log().all();
		AssertJUnit.assertEquals(postresponse.statusCode(), 200);
		jsPost= new JsonPath(postresponse.asString());
		int val = jsPost.getInt("code");
		AssertJUnit.assertEquals(val, 200);
		// Validations:- Check if response status code is 200, Check if POST request is
		// same as GET Response
		Response getResponse = UserEndpoints.GetUser(payload.getUsername());
		getResponse.then().log().all();
		AssertJUnit.assertEquals(getResponse.statusCode(), 200);
		jsGet = new JsonPath(getResponse.asString());
		AssertJUnit.assertEquals(payload.getId(), jsGet.getInt("id"));
		AssertJUnit.assertEquals(payload.getUserStatus(), jsGet.getInt("userStatus"));
		AssertJUnit.assertEquals(payload.getEmail(), jsGet.getString("email"));
		AssertJUnit.assertEquals(payload.getFirstName(), jsGet.getString("firstName"));
		AssertJUnit.assertEquals(payload.getLastName(), jsGet.getString("lastName"));
	}

	@DataProvider(name = "DataFetching")
	public Object[][] GetData() {
		Object[][] obj = { { 20 }, { 40 } };
		return obj;
	}
}
