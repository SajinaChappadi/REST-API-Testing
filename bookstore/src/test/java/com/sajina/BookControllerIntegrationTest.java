package com.sajina;

import com.google.gson.Gson;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class BookControllerIntegrationTest {

    @BeforeClass
	public static void beforeClass() {
		Main.main(null);
	}

	@AfterClass
	public static void afterClass() {
		Spark.stop();
	}

	@Test
	public void aNewBookShouldBeCreated() {
		TestResponse res = request("POST", "/books?id=D20001&name=StartWithWhy&author=SimonSinek&price=$30.00");
		Map<String, String> json = res.json();
		assertEquals(200, res.status);
		assertEquals("StartWithWhy", json.get("name"));
        assertEquals("SimonSinek", json.get("author"));
        assertEquals("$30.00", json.get("price"));
		assertNotNull(json.get("id"));
	}

	@Test(expected = AssertionError.class)
    public void requestPOSTEmptyId_checkResponseBody_aMessage(){
    request("POST", "/users?name=StartWithWhy&author=SimonSinek&price=$30.00");  
    }

	@Test(expected = AssertionError.class)
    public void requestPOSTEmptyName_checkResponseBody_aMessage(){
    request("POST", "/users?id=D20001&author=SimonSinek&price=$30.00");  
    }

    @Test(expected = AssertionError.class)
    public void requestPOSTEmptyAuthor_checkResponseBody_aMessage(){
    request("POST", "/users?id=D20001&name=StartWithWhy&price=$30.00");  
	}
	
	@Test(expected = AssertionError.class)
    public void requestPOSTEmptyPrice_checkResponseBody_aMessage(){
    request("POST", "/users?id=D20001&name=StartWithWhy&author=SimonSinek");  
    }

	@Test
    public void requestGETABook_checkResponseBody_expectAtomichabits(){
        TestResponse res = request("GET", "/books/B10001");
        Map<String, String> json = res.json();
        assertEquals(200, res.status);
		assertEquals("Atomic habits", json.get("name"));
	    
	}

	@Test
    public void requestGETBooks_checkResponseBody_expectAllBooks(){
		TestResponse res = request("GET", "/books");	
		String actual = res.body;
		System.out.println(actual);
        assertEquals(200, res.status);
		String book[] = actual.split("}");
		System.out.println(book[0]);
		assertEquals(book[0].contains("B10001"),true);
		assertEquals(book[1].contains("D20001"),true);
	}
	
	@Test
	public void requestUpdateBookPrice_checkResponseBody_expectUpdatedPrice(){
		TestResponse res = request("PUT", "/books/B10001?name=AtomicHabits&author=JamesClear&price=$40.00");
		Map<String, String> json = res.json();
		assertEquals(200, res.status);
		assertEquals("$40.00", json.get("price"));
	}

	@Test
	public void requestDELETEBook_checkResponseBody_expectMessage(){
		TestResponse res = request("DELETE", "/books/B10001");
		String message = res.body;
		System.out.println("Message is"+message);		
		assertEquals(200, res.status);
		assertEquals(message.contains("Book deleted"), true);
		
	} 


	private TestResponse request(String method, String path) {
		try {
			URL url = new URL("http://localhost:4567" + path);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(method);
			connection.setDoOutput(true);
			connection.connect();
			String body = IOUtils.toString(connection.getInputStream());
			return new TestResponse(connection.getResponseCode(), body);
		} catch (IOException e) {
			e.printStackTrace();
			fail("Sending request failed: " + e.getMessage());
			return null;
		}
	}

	private static class TestResponse {

		public final String body;
		public final int status;

		public TestResponse(int status, String body) {
			this.status = status;
			this.body = body;
		}

		public Map<String,String> json() {
			return new Gson().fromJson(body, HashMap.class);
		}
	}
}
