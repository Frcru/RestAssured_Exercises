package test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

public class SwapiTest {
    private Response response = RestAssured.when().get("https://swapi.dev/api/people/2");

    @Test
    public void getSwApi() {

        response = given().get("https://swapi.dev/api/people/1");
        String height = response.jsonPath().getString("height");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(height, "172");
    }

    @Test
    //1. Test the endpoint people/2/ and check the success response,
    // the skin color to be gold, and the amount of films
    // it appears on (should be 6).
    public void isBlond() {

        String hairColor = response.jsonPath().getString("skin_color");

        Assert.assertEquals(hairColor, "gold");

        List<String> movieList = response.jsonPath().getList("films");
        List<String> expected = new ArrayList<>();

        for (int i = 1; i <= 6; i++) {
            expected.add(String.format("https://swapi.dev/api/films/%s/", i));
            System.out.println(expected.get(i - 1));
        }

        for (int i = 0; i < 5; i++) {

            Assert.assertEquals(movieList.get(i), expected.get(i));
        }
        Assert.assertEquals(movieList.size(), 6);
        System.out.println(movieList.size());

    }

    //2. Request the endpoint of the second movie in which people/2/ was present
// (using the response from people/2/). Check the release date to be in the
// correct date format, and the response to include characters, planets,
// starships, vehicles and species, each element including more than 1 element.
    @Test
    public void checkRealeseDate() {

        List<String> moviesPresentList = response.jsonPath().getList("films");
        String secondMovie = moviesPresentList.get(1);
       response = given().get(secondMovie);
        // response =RestAssured.when().get("https://swapi.dev/api/films/2");
        List<String> comparecharList = new ArrayList<>();

        comparecharList.add("https://swapi.dev/api/people/1/");
        comparecharList.add("https://swapi.dev/api/people/2/");
        comparecharList.add("https://swapi.dev/api/people/3/");
        comparecharList.add("https://swapi.dev/api/people/4/");
        comparecharList.add("https://swapi.dev/api/people/5/");
        comparecharList.add("https://swapi.dev/api/people/10/");
        comparecharList.add("https://swapi.dev/api/people/13/");
        comparecharList.add("https://swapi.dev/api/people/14/");
        comparecharList.add("https://swapi.dev/api/people/18/");
        comparecharList.add("https://swapi.dev/api/people/20/");
        comparecharList.add("https://swapi.dev/api/people/21/");
        comparecharList.add("https://swapi.dev/api/people/22/");
        comparecharList.add("https://swapi.dev/api/people/23/");
        comparecharList.add("https://swapi.dev/api/people/24/");
        comparecharList.add("https://swapi.dev/api/people/25/");
        comparecharList.add("https://swapi.dev/api/people/26/");

        List<String> compareplanetList = new ArrayList<>();

        compareplanetList.add("https://swapi.dev/api/planets/4/");
        compareplanetList.add("https://swapi.dev/api/planets/5/");
        compareplanetList.add("https://swapi.dev/api/planets/6/");
        compareplanetList.add("https://swapi.dev/api/planets/27/");

        List<String> comparevehicleList = new ArrayList<>();

        comparevehicleList.add("https://swapi.dev/api/vehicles/8/");
        comparevehicleList.add("https://swapi.dev/api/vehicles/14/");
        comparevehicleList.add("https://swapi.dev/api/vehicles/16/");
        comparevehicleList.add("https://swapi.dev/api/vehicles/18/");
        comparevehicleList.add("https://swapi.dev/api/vehicles/19/");
        comparevehicleList.add("https://swapi.dev/api/vehicles/20/");

        List<String> comparespecieList = new ArrayList<>();
        comparespecieList.add("https://swapi.dev/api/species/1/");
        comparespecieList.add("https://swapi.dev/api/species/2/");
        comparespecieList.add("https://swapi.dev/api/species/3/");
        comparespecieList.add("https://swapi.dev/api/species/6/");
        comparespecieList.add("https://swapi.dev/api/species/7/");


        String releaseDate = response.jsonPath().getString("release_date");
        List<String> characterList = response.jsonPath().getList("characters");
        List<String> planetList = response.jsonPath().getList("planets");
        List<String> vehicleList = response.jsonPath().getList("vehicles");
        List<String> specieList = response.jsonPath().getList("species");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        //year validation
        try {
            formatter.parse(releaseDate);
            Assert.assertTrue(true);

        } catch (ParseException e) {
            Assert.assertTrue(false);
        }

        for (int i = 0; i < comparecharList.size(); i++) {
            Assert.assertEquals(characterList.get(i),comparecharList.get(i));
        }

        for (int i = 0; i < comparevehicleList.size(); i++) {
            Assert.assertEquals(vehicleList.get(i),comparevehicleList.get(i));
        }

        for (int i = 0; i < compareplanetList.size(); i++) {
            Assert.assertEquals(planetList.get(i),compareplanetList.get(i));
        }

        for (int i = 0; i < comparespecieList.size(); i++) {
            Assert.assertEquals(specieList.get(i),comparespecieList.get(i));
        }

    }

    // 3. Request the endpoint of the first planet present in the last
    // film's response (using the previous response). Check the gravity and
    // the terrains matching the exact same values returned by the request
    // (Use fixtures to store and compare the data of terrains and gravity).
    @Test

    public void isFilmFound() {
        //consulting endpoint of people/2/ then getting filmList by jsonPath to reach the last film
        List<String> filmList = response.jsonPath().getList("films");
        String lastFilmResponse = filmList.get(filmList.size()-1);

        response = given().get(lastFilmResponse);
        List<String> planetList = response.jsonPath().getList("planets");

        String firstPlanet = planetList.get(0);
        response = RestAssured.get(firstPlanet);
        String gravity = response.jsonPath().getString("gravity");
        String terrain =  response.jsonPath().getString("terrain");

        Assert.assertEquals(gravity,"1 standard");
        Assert.assertEquals(terrain,"desert");
        //4.On the same response from the planet, grab the url element on the response,
        // and request it. Validate the response being exactly the same from the previous one.
        System.out.println("Validating consult planet");
        System.out.println(firstPlanet);
        Assert.assertEquals(firstPlanet,"https://swapi.dev/api/planets/1/");
        //5.Request the /films/7/ and check the response having a 404 code.
        Assert.assertEquals(RestAssured.get("https://swapi.dev/api/films/7/").getStatusCode(),404);
    }
}
