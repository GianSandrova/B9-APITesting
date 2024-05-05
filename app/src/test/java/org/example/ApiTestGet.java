package org.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTestGet {

    // Base URL of the API
    private static final String BASE_URL = "https://dummyapi.io/data/v1";

    // Dummy app-id for authorization
    private static final String APP_ID = "663233b5265d5f233cbade2f";

    @Test
    public void test_TC1_negative_missingAppId() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(BASE_URL + "/user/60d0fe4f5311236168a109cd")
                .then()
                .statusCode(401)
                .body("error", equalTo("APP_ID_MISSING"));
    }

    @Test
    public void test_TC2_positive_userExists() {
        given()
                .contentType(ContentType.JSON)
                .header("app-id", APP_ID)
                .when()
                .get(BASE_URL + "/user/60d0fe4f5311236168a109cd")
                .then()
                .statusCode(200)
                .body("id", equalTo("60d0fe4f5311236168a109cd"));
    }

    @Test
    public void test_TC3_negative_invalidUserId() {
        given()
                .contentType(ContentType.JSON)
                .header("app-id", APP_ID)
                .when()
                .get(BASE_URL + "/user/60d0fe4f5311236168a109cd=")
                .then()
                .statusCode(400)
                .body("error", equalTo("PARAMS_NOT_VALID"));
    }

    @Test
    public void test_TC4_negative_emptyUserId() {
        given()
                .contentType(ContentType.JSON)
                .header("app-id", APP_ID)
                .when()
                .get(BASE_URL + "/user/")
                .then()
                .statusCode(404)
                .body("error", equalTo("PATH_NOT_FOUND"));
    }

    @Test
    public void test_TC5_negative_userNotFound() {
        given()
                .contentType(ContentType.JSON)
                .header("app-id", APP_ID)
                .when()
                .get(BASE_URL + "/user/1234567890abc123456789c")
                .then()
                .statusCode(400)
                .body("error", equalTo("PARAMS_NOT_VALID"));
    }

    @Test
    public void test_TC6_negative_deletedUser() {
        given()
                .contentType(ContentType.JSON)
                .header("app-id", APP_ID)
                .when()
                .get(BASE_URL + "/user/60d0fe4f5311236168a109cc")
                .then()
                .statusCode(404)
                .body("error", equalTo("RESOURCE_NOT_FOUND"));
    }
}
