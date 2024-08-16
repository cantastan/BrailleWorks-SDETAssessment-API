package brailleWorks.api;

import brailleWorks.api.utility.weatherForecastTestBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;
import io.restassured.path.json.JsonPath;

import java.util.LinkedHashMap;
import java.util.Map;

/*
THERE ARE MANY WAYS TO ORGANIZE AN API FRAMEWORK CUCUMBER BDD IS ONE AND IS CLEAN
I HAVE PROVIDED DIFFERENT WAYS TO WRITE THE TEST CASES AND OF COURSE DEPENDING ON THE SCENARIO AND USER
REQUIREMENTS THIS CAN AND WILL CHANGE
 */
// extending abstract class to use @BeforeAll method to gain access to baseURI - HOST URL
public class weatherForecast extends weatherForecastTestBase {

    // starting off simple
    String url = "http://localhost:8081/weatherforecast";

                /*
                GET /weatherforecast
                • Description: Retrieves a list of all weather forecasts.
                • Parameters: None
                • Response: A JSON array of weather forecast objects. Each object includes an Id, Date, TemperatureC, TemperatureF, and Summary.

                When user sends a request to http://localhost:8081/weatherforecast/ endpoint
                Then user should be able to see status code is 200
                And print out JSON response body into screen/console
                */

    @DisplayName("GET /weatherforecast")
    @Order(1)
    @Test
    public void getWeatherForecast() { // SIMPLE WAY WITHOUT BASEURI OR GHERKIN

        // send request to url and get response as Response interface
        Response responseGet = RestAssured.get(url);
        System.out.println("Status Code = " + responseGet.statusCode());

        //create int variable to store status code
        int expectedStatusCode = 200;
        int actualStatusCode = responseGet.statusCode();

        //create Assertions to compare if status code is exactly equalled to expected 200
        Assertions.assertEquals(expectedStatusCode, actualStatusCode); // will fail test if false
        Assertions.assertEquals("application/json; charset=utf-8", responseGet.contentType());

        //print json response body to console
        responseGet.prettyPrint();// will automatically print, print statement not needed
    }

                /*
                GET /weatherforecast/{id}
                • Description: Retrieves a specific weather forecast by its unique identifier.
                • Parameters:
                • id (path parameter): The unique identifier of the weather forecast to retrieve.
                • Response: If found, returns a single weather forecast object in JSON format, including Id, Date, TemperatureC, TemperatureF, and Summary. If not found, returns a 404 Not Found status.

                Given accept type is Json
                and ID parameter value is 1
                When user sends a GET request to baseURI/{id} endpoint
                Then user should be able to see status code is 200
                and response content-type: application/json; charset=utf-8
                And print out JSON response body parameter id "1" into screen/console payload(body)
                */

    @DisplayName("GET /weatherforecast/{id} path pos param {1}")
    @Order(2)
    @Test
    public void getWeatherForecastParametersPositive(){ // minor cucumber function, gherkin
        //this will use Response interface and RestAssured libraries to organise requirements
        Response responseGet = given()
                .accept(ContentType.JSON)// you want content type JSON
                .and() // a fluffer/sugar does nothing but makes more readable
                .pathParam("id",1)// you want parameter 1
                .when() // a fluffer/sugar does nothing but makes more readable
                .get("/{id}"); //dynamically input {id}1 into host url

        // create assertions based on requirements
        int expectedStatusCode = 200;
        int actualStatusCode = responseGet.statusCode();

        if(actualStatusCode != expectedStatusCode){
            System.out.println("Expected status code " + expectedStatusCode + " but got " + actualStatusCode);
        }else{
            responseGet.prettyPrint();
        }

        //created assertions after if statement just incase test fails if statement will still run
        Assertions.assertEquals(expectedStatusCode, actualStatusCode);
        Assertions.assertEquals("application/json; charset=utf-8", responseGet.contentType());


    }

                /*
                GET /weatherforecast/{id}
                • Description: Retrieves a specific weather forecast by its unique identifier.
                • Parameters:
                • id (path parameter): The unique identifier of the weather forecast to retrieve.
                • Response: If found, returns a single weather forecast object in JSON format, including Id, Date, TemperatureC, TemperatureF, and Summary.
                *****If not found, returns a 404 Not Found status.***** focusing on this

                Given accept type is Json
                and ID parameter value is 0
                When user sends a GET request to baseURI/{id} endpoint
                Then user should be able to see status code is 404
                */

    // im creating this just as extra to show code 404 but in real world of course since {id} 0 doesnt exist the Assertion will Fail with code 404 along with Test Case Failing
    @DisplayName("GET /weatherforecast/{id} path neg param {0}")
    @Order(3)
    @Test
    public void getWeatherForecastParametersNegative(){ // minor cucumber function, gherkin
        //this will use Response interface and RestAssured libraries to organise requirements
        Response responseGet = given()
                .accept(ContentType.JSON)// you want api to send json response
                .and()
                .pathParam("id",0)// you want parameter 0(doesnt exist)
                .when()
                .get("/{id}");//dynamically input {id}0 into host url

        // create assertions based on requirements
        int expectedStatusCode = 404;
        int actualStatusCode = responseGet.statusCode();
        System.out.println("actualStatusCode = " + actualStatusCode);

        if(actualStatusCode != expectedStatusCode){
            System.out.println("Expected status code " + expectedStatusCode + " but got " + actualStatusCode);
        }else{
            System.out.println("Status code = " + actualStatusCode + " Not Found");
        }

        Assertions.assertEquals(expectedStatusCode, actualStatusCode);
    }

                /*
               POST /weatherforecast
                • Description: Creates a new weather forecast.
                • Parameters: A JSON object in the request body with the following properties:
                  - Date (DateOnly): The date of the forecast. Formatted "YYYY-MM-DD"
                  - TemperatureC (int): The temperature in Celsius.
                  - Summary (enum): A summary of the weather conditions.
                • Response: Returns a 201 Created status with the created weather forecast object in the response body, including a Location header pointing to the newly created forecast's URI.

                 Given accept type is JSON
                 And Content type is JSON
                 And request json body is:
                 {
                "date": "2024-08-16",
                "temperatureC": 40,
                "summary": "Scorching"
                 }
                 When user sends POST request to BaseURI
                 Then status code 201
                 And content type should be application/json; charset=utf-8
                 And json payload/response/body should contain:
                "date": "2024-08-16",
                "temperatureC": 40,
                "summary": "Scorching"
                 */

    @DisplayName("POST /weatherforecast post par {4}")
    @Order(4)
    @Test
    public void postWeatherForecastParameters(){
        //simple string, this can be a Map, Class, ect...
        /*String requestBody = "   {\n" +
                "    \"date\": \"2024-08-16\",\n" +
                "    \"temperatureC\": 40,\n" +
                "    \"summary\": \"Scorching\"\n" +
                "}";
        */
        //              OR OR OR
        //Map way is much cleaner i created String version above for reference
        Map<String, Object> requestBody = new LinkedHashMap<>(); // keep the order of inputs
        requestBody.put("date","2024-08-16");
        requestBody.put("temperatureC","40");
        requestBody.put("summary","Scorching");
        //created one Map and put the info that we want to send as a JSON REQUEST BODY

        // using Junit, Hamcrest, and gherkin makes for cleaner code (converting Java to Json)
        JsonPath jsonPathPost = given()
                .accept(ContentType.JSON) //hey api, please send me JSON RESPONSE BODY
                .and() // sugar means nothing just more readable
                .contentType(ContentType.JSON) // hey api, I am sending you JSON REQUEST BODY
                .body(requestBody) // will input the post request from String variable above
                .when() // sugar means nothing just more readable
                .post().prettyPeek() //will post to api and show full headers and body/payload
                .then() // validatable response everything after is like an assertion thank you Hamcrest Matchers
                .statusCode(201) //201 means post request passed Created
                .contentType("application/json; charset=utf-8")
                .body("date", is("2024-08-16"),
                        "temperatureC",is(40),
                        "summary",is("Scorching"))
                .extract().jsonPath();

        // will verify that the POST request date is correct from user request
        Assertions.assertEquals("2024-08-16", jsonPathPost.get("date"));
        // will verify that POST request temperatureC int input is correct from user request
        Assertions.assertEquals(40, jsonPathPost.getInt("temperatureC"));
        // will verify that POST request summary input of weather forecast is correct from user request
        Assertions.assertEquals("Scorching", jsonPathPost.get("summary"));
    }

}
