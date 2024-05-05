package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTestCreate {

    private static final String BASE_URL = "https://dummyapi.io/data/v1";

    // Dummy app-id for authorization
    private static final String APP_ID = "663776974a807b5e8547b9d2";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    public void testNegativeNoAppId() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"firstName\":\"Mikasa\",\"lastName\":\"Ackerman\",\"email\":\"mikasaackerman123@example.com\"}")
                .when()
                .post("/user/create")
                .then()
                .assertThat()
                .statusCode(403)
                .body("error", equalTo("APP_ID_MISSING"));
    }
    @Test
    public void testNegativeEmptyFirstName() {
        given()
                .contentType(ContentType.JSON)
                .header("app-id", APP_ID)  // Include the app-id header as it's likely required for all requests
                .body("{\"firstName\":\"\", \"lastName\":\"Eren\", \"email\":\"eren98765@example.com\"}")
                .when()
                .post("/user/create")
                .then()
                .assertThat()
                .statusCode(400)
                .body("error", equalTo("BODY_NOT_VALID"));
    }
    @Test
    public void testNegativeEmptyLastName() {
        given()
                .contentType(ContentType.JSON)
                .header("app-id", APP_ID)  // Include the app-id header as it's likely required for all requests
                .body("{\"firstName\":\"Eren\", \"lastName\":\"\", \"email\":\"eren54321@example.com\"}")
                .when()
                .post("/user/create")
                .then()
                .assertThat()
                .statusCode(400)
                .body("error", equalTo("BODY_NOT_VALID"));
    }
    @Test
    public void testNegativeEmptyEmail() {
        given()
                .contentType(ContentType.JSON)
                .header("app-id", APP_ID)  // Include the app-id header to ensure the request is authorized
                .body("{\"firstName\":\"Eren\", \"lastName\":\"Ackerman\", \"email\":\"\"}")
                .when()
                .post("/user/create")
                .then()
                .assertThat()
                .statusCode(400)
                .body("error", equalTo("BODY_NOT_VALID"));
    }
    @Test
    public void testNegativeAllFieldsEmpty() {
        given()
                .contentType(ContentType.JSON)
                .header("app-id", APP_ID)  // Ensure to include the app-id for authorization
                .body("{\"firstName\":\"\", \"lastName\":\"\", \"email\":\"\"}")
                .when()
                .post("/user/create")
                .then()
                .assertThat()
                .statusCode(400)
                .body("error", equalTo("BODY_NOT_VALID"));
    }

}

