package test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import org.junit.Test;
import org.testng.Assert;

import static org.hamcrest.Matchers.is;

public class RestAssuredApiTest {
    @Test
    public void verifyFindUSAUsingCodeUS() {
        Response response = RestAssured.get("https://65443c285a0b4b04436c2fe6.mockapi.io/api/v1/users/8/");


        response.
                then().assertThat().statusCode(200)
                .and().assertThat().body("RestResponse.result.alias", is("Silvina"));
    }

    @Test
    public void verifying(){
        Response response = RestAssured.get("https://65443c285a0b4b04436c2fe6.mockapi.io/api/v1/users/8/");
        String alias;
        alias =response.jsonPath().getString("alias");
        Assert.assertEquals(alias,"Silvina");
    }
}

