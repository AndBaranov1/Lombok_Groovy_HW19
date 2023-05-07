package api.tests;

import api.lombok.LombokUserData;
import org.junit.jupiter.api.Test;

import static api.spec.Specs.requestSpec;
import static api.spec.Specs.responseSpec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReqresUsersTest {

    @Test
    public void getCheckUserEmailGroovy() {
        given()
                .spec(requestSpec)
                .when()
                .get("/users")
                .then()
                .log().body()
                .body("data.findAll{it.email =~/.*?@reqres.in/}.email.flatten()",
                        hasItem("tracey.ramos@reqres.in"));
    }

    @Test
    void getSingleUser() {
        given()
                .spec(requestSpec)
                .when()
                .get("/users/2")
                .then()
                .spec(responseSpec)
                .log().body();
    }

    @Test
    void getListOfUsers() {
        given()
                .spec(requestSpec)
                .when()
                .get("/users?page=2")
                .then()
                .log().body();
    }


    @Test
    void singleUserWithLombokModel() {
        LombokUserData data = given()
                .spec(requestSpec)
                .when()
                .get("/users/2")
                .then()
                .spec(responseSpec)
                .log().body()
                .extract().as(LombokUserData.class);
        assertEquals(2, data.getUser().getId());
    }

    @Test
    public void deleteUser() {
        given()
                .spec(requestSpec)
                .when()
                .delete("/users/2")
                .then()
                .statusCode(204);
    }

}
