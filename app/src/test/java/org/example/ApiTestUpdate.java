package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTestUpdate {

    private static final String BASE_URL = "https://dummyapi.io/data/v1";

    // Dummy app-id for authorization
    private static final String APP_ID = "663233b5265d5f233cbade2f";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void testPositiveUpdateWithCountry2Characters() {
        given()
                .header("app-id", APP_ID)
                .contentType(ContentType.JSON)
                .body("{\"location\":{\"country\":\"AB\"}}")
                .when()
                .put("/user/60d0fe4f5311236168a109fa")
                .then()
                .assertThat()
                .statusCode(200)
                .body("location.country", equalTo("AB"));
    }

    @Test
    public void testPositiveUpdateWithCountry30Characters() {
        given()
                .header("app-id", APP_ID)
                .contentType(ContentType.JSON)
                .body("{\"location\":{\"country\":\"QWERTYUIOPASDFGHJKLZXCVBNMQWER\"}}")
                .when()
                .put("/user/60d0fe4f5311236168a109fa")
                .then()
                .assertThat()
                .statusCode(200)
                .body("location.country", equalTo("QWERTYUIOPASDFGHJKLZXCVBNMQWER"));
    }

    @Test
    public void testPositiveUpdateWithValidTimezone() {
        given()
                .header("app-id", APP_ID)
                .contentType(ContentType.JSON)
                .body("{\"location\":{\"timezone\":\"+7:00\"}}")
                .when()
                .put("/user/60d0fe4f5311236168a109fa")
                .then()
                .assertThat()
                .statusCode(200)
                .body("location.timezone", equalTo("+7:00"));
    }

    @Test
    public void testNegativeUpdateWithNonExistingId() {
        given()
                .header("app-id", APP_ID)
                .contentType(ContentType.JSON)
                .body("{\"id\":\"60d0fe4f5311236168a109fa\",\"title\":\"mr\",\"firstName\":\"Gian\",\"lastName\":\"Sandrova\",\"picture\":\"https://randomuser.me/api/portraits/med/women/18.jpg\",\"gender\":\"Male\",\"dateOfBirth\":\"2003-07-28\",\"phone\":\"(385)-245-2517\",\"location\":{\"street\":\"2698, Paddock Way\",\"city\":\"Orange\",\"state\":\"Wyoming\",\"country\":\"United States\",\"timezone\":\"+3:00\"}}")
                .when()
                .put("/user/60d0fe4f5311236168a109faaaa")
                .then()
                .assertThat()
                .statusCode(404)
                .body("error", equalTo("RESOURCE_NOT_FOUND"));
    }

    @Test
    public void testNegativeUpdateWithEmailFieldFilled() {
        given()
                .header("app-id", APP_ID)
                .contentType(ContentType.JSON)
                .body("{\"email\":\"sandrova@gmail.com\"}")
                .when()
                .put("/user/60d0fe4f5311236168a109fa")
                .then()
                .assertThat()
                .statusCode(400)
                .body("error", equalTo("BODY_NOT_VALID"));
    }
}
