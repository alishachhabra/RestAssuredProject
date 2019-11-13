package test;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestTEst {
	
	//@Test
	public void getDemo()
	{
		 // Specify the base URL to the RESTful web service
		 RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";
		 
		 // Get the RequestSpecification of the request that you want to sent
		 // to the server. The server is specified by the BaseURI that we have
		 // specified in the above step.
		 RequestSpecification request = RestAssured.given();
		 
		
		 
		 // Make a request to the server by specifying the method Type and the method URL.
		 // This will return the Response from the server. Store the response in a variable.
		 //Response response = httpRequest.request(Method.GET, "/Hyderabad");
		 Response response = request.get("/Hyderabad");
		
		 
		 // Now let us print the body of the message to see what response
		 // we have recieved from the server
		 String responseBody = response.getBody().asString();
	
		 System.out.println("Status code: "+response.getStatusCode());
		 System.out.println("Response Body is =>  " + responseBody);
	}
	
	@SuppressWarnings("unchecked")
	//@Test
	public void postDemo()
	{
		RestAssured.baseURI ="http://restapi.demoqa.com/customer";
		 RequestSpecification request = RestAssured.given();
		 
		 JSONObject requestParams = new JSONObject();
		 requestParams.put("FirstName", "puja123"); 
		 requestParams.put("LastName", "test1");
		 requestParams.put("UserName", "pujapleurrrtr2ddppp123");
		 requestParams.put("Password", "password1");
		 
		 requestParams.put("Email",  "test123lamirp@gmail.com");
		request.body(requestParams.toJSONString());
		 Response response = request.post("/register");
		System.out.println("response.body() is " +response.getBody().asString()); 
		 
		 int statusCode = response.getStatusCode();
		 System.out.println("statusCode is " +statusCode);
		 String successCode = response.jsonPath().get("Success code");
		 System.out.println("successCode is " +successCode);
	}
	
	@SuppressWarnings("unchecked")
	//@Test
	public void putDemo()
	{
		int empid = 15410;
		 
		 RestAssured.baseURI ="http://dummy.restapiexample.com/api/v1";
		 RequestSpecification request = RestAssured.given();
		 
		 JSONObject requestParams = new JSONObject();
		 requestParams.put("name", "Zion"); // Cast
		 requestParams.put("age", 23);
		 requestParams.put("salary", 120);
		 
		 request.header("Content-Type", "application/json"); 
		request.body(requestParams);
		 Response response = request.put("/update/"+ empid);
		 
		 int statusCode = response.getStatusCode();
		 System.out.println(response.asString());
		 Assert.assertEquals(statusCode, 200); 
	}

	@Test
	public void deleteDemo()
	{
		int empid = 15410;
		 
		 RestAssured.baseURI ="http://dummy.restapiexample.com/api/v1";
		 RequestSpecification request = RestAssured.given();

		 
		// request.header("Content-Type", "application/json"); 
		 Response response = request.delete("/delete/"+ empid);
		 
		 int statusCode = response.getStatusCode();
		 System.out.println(response.asString());
		 Assert.assertEquals(statusCode, 200); 
		 
		 Assert.assertEquals(response.asString().contains("successfully! deleted Records"), true);
		 
		 Assert.assertEquals(response.jsonPath().get("success.text"),"successfully! deleted Records");
	}


}
