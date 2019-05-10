package test;

import javax.xml.validation.Validator;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;
import listeners.Retry;

public class RestTest2 {
	
	@Test(priority =1, retryAnalyzer = Retry.class)
	public void getReq()
	{
		RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";

		RequestSpecification httpRequest = RestAssured.given();
		
		Response response = httpRequest.request(Method.GET, "/employees");

		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);
	}
	
	@Test(priority =2, retryAnalyzer = Retry.class)
	public void getReq2()
	{
		int id = 38588;
		RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";

		RequestSpecification httpRequest = RestAssured.given();
		Response response = httpRequest.request(Method.GET, "/employee/"+id);

		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);
	}
	
	
	@SuppressWarnings("unchecked")
	@Test(priority =3, retryAnalyzer = Retry.class)
	public void postReq()
	{
		RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";

		RequestSpecification httpRequest = RestAssured.given();

		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "tarzan"); 
		requestParams.put("salary", "500");
		requestParams.put("age", "50");

		httpRequest.body(requestParams.toJSONString());

		Response response = httpRequest.request(Method.POST, "/create");
		System.out.println("response.body() is " +response.getBody().asString()); 
		int statusCode = response.getStatusCode();
		System.out.println("statusCode is " +statusCode);
		JsonPath jsonPatheva = response.jsonPath();
		String id = jsonPatheva.get("id");
		System.out.println("id created is " +id);
		
		String nm = jsonPatheva.get("name");
		System.out.println("name created is " +nm);
		Assert.assertEquals(true, nm.equalsIgnoreCase("tarzan"));
		Response response1 = httpRequest.request(Method.GET, "/employee/"+id);
		String getBody = response1.getBody().asString();
		System.out.println("get body is " +getBody);
		Assert.assertEquals(true, getBody.contains(id));

	}
	
	
	@SuppressWarnings("unchecked")
	@Test(priority =4, retryAnalyzer = Retry.class)
	public void putReq()
	{
		 RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";
		 RequestSpecification httpRequest = RestAssured.given();		 
		 JSONObject requestParams = new JSONObject();
		 requestParams.put("name", "test12345"); 
		 requestParams.put("salary", "10000");
		 requestParams.put("age", "5");
		 httpRequest.body(requestParams.toJSONString());	
		 Response response = httpRequest.request(Method.PUT, "/update/37788");
			System.out.println("response.body() is " +response.getBody().asString()); 
			int statusCode = response.getStatusCode();
		 System.out.println("statusCode is " +statusCode);
		 JsonPath jsonPatheva = response.jsonPath();
		 String age = jsonPatheva.get("age");
		 System.out.println("age is " +age);
		Assert.assertEquals(true, age.equalsIgnoreCase("5"));

	}

	
	
	@Test(priority =5, retryAnalyzer = Retry.class)
	public void deleteReq()
	{
		 RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";
		 RequestSpecification httpRequest = RestAssured.given();	
		 Response response = httpRequest.request(Method.DELETE, "/delete/37929");
		 System.out.println("response.body() is " +response.getBody().asString()); 
		 String body = response.getBody().asString();
		 int statusCode = response.getStatusCode();
		 System.out.println("statusCode is " +statusCode);
		 System.out.println("-----------" +body);


	}
}
