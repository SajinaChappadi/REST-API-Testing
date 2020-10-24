package com.sajina;

import com.tngtech.java.junit.dataprovider.*;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


@RunWith(DataProviderRunner.class)
public class BookstoreRestAssuredTests {
    
    @DataProvider
    public static Object [][] bookIdAndNames() {
        return new Object[][] {
            {"B10001","Atomic habits", "James Clear", "$25.50"},
            {"D20001","Start with why", "Simon Sinek", "$20.00"}
        };
    }

    private static RequestSpecification requestSpec;

    @BeforeClass
    public static void createRequestSpecification(){
        requestSpec = new RequestSpecBuilder().
        setBaseUri("http://localhost:4567/books").
        build();
    }
   
    private static ResponseSpecification responseSpec;

    @BeforeClass
    public static void createResponseSpecification(){
        responseSpec = new ResponseSpecBuilder().
            expectStatusCode(200).
            expectContentType(ContentType.JSON).
            build();
    }

   
    
    @Test
    @UseDataProvider("bookIdAndNames")
    public void requestABookIdFromCollection_checkBookNameInResponseBody_expectSpecifiedName_withResponseSpec(String id, String expectedBookName,String expectedAuthor,String expectedPrice){
        given().
        spec(requestSpec).
        and().
        pathParam("id", id).

        when().
        get("/{id}").

        then().
        spec(responseSpec).
        and().
        assertThat().
        body("name",equalTo(expectedBookName)).
        and().
        body("author",equalTo(expectedAuthor)).
        and().
        body("price",equalTo(expectedPrice));
        
    }


    @Test
    public void requestAllTheBooks_checkListOfNamesAndSizeInResponseBody_expectContainsStartwithwhy(){

        given().

        when().
        get("http://localhost:4567/books").

        then().
        spec(responseSpec).
        and().
        assertThat().
        body("[0].name", equalTo("Atomic habits")).
        body("[1].name", equalTo("Start with why")).
        and().
        body("name", hasSize(2));
    }

    @Test
    public void whenRequestedPost_checkIdAndNameInResponsebody_expectD20001AndStartwithwhy_withResponseSpec(){
            given().
			
			when().
            post("http://localhost:4567/books?name=Start with why&author=Simon Sinek&price=$20.00&id=D20001"). 
            then(). 
            spec(responseSpec).
            and().
            assertThat().    
            body("id",equalTo("D20001")).
            body("name", equalTo("Start with why"));     
    }

    @Test
    public void whenRequestedPut_checkPriceInResponseBody_expect$35_withResponseSpec(){

        given().
		spec(requestSpec).	
        when().
        put("/D20001?name=Start with why&author=Simon Sinek&price=$35.00&id=D20001"). 
        then(). 
        spec(responseSpec).
        and().
        assertThat().    
        body("price",equalTo("$35.00"));
        }


        @Test
        public void whenRequestDelete_checkMessageInResponseBody_expectMessage_withResponseSpec(){
        Response response = 
        
        given().
		spec(requestSpec).	
        when().
        delete("/D20001");

        Assert.assertEquals("Book deleted", response.asString());
        Assert.assertEquals(200, response.getStatusCode());
       
        
        //check if id= D20001 is deleted
        given().

        when().
        get("http://localhost:4567/books").

        then().
        spec(responseSpec).
        and().
        assertThat().
        body("id", not(equalTo("D20001")));
        
        }
}
