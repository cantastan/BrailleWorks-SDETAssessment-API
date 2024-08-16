package brailleWorks.api.utility;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public abstract class weatherForecastTestBase {

    /*
    this will run before all test annotations and the .get() method will automatically recognize baseURI
    I would create this base class with this method and then extend it to my test cases great for being dynamic or if host changes you can just come here and change it VS going into EACH test case. You can also create properties file with key=value pair and store the host uri in there as well in the event you interact with multiple api's.
    */

    @BeforeAll
    public static void init(){ // init - initialization
        RestAssured.baseURI = "http://localhost:8081/weatherforecast/";
    }

}
