package Board;

import base.BaseTest;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

import static org.assertj.core.api.Assertions.*;

public class BoardTest extends BaseTest {

    private static String boardId;

    static void deleteBoard() {
        given()
                .spec(reqSpec)
                .when()
                .delete(BASE_URL + BOARDS_ENDPOINT + boardId)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void createNewBoard() {
        Response response = given()
                .spec(reqSpec)
                .queryParam("name", "My second board")
                .when()
                .post(BASE_URL + BOARDS_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo("My second board");

        boardId = json.get("id");

        deleteBoard();
    }

    @Test
    public void createBoardWithEmptyName() {
        Response response = given()
                .spec(reqSpec)
                .queryParam("name", "")
                .when()
                .post(BASE_URL + BOARDS_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .response();
    }

    @Test
    public void createBoardWithoutDefaultLists() {
        Response response = given()
                .spec(reqSpec)
                .queryParam("name", "Board without default lists")
                .queryParam("defaultLists", false)
                .when()
                .post(BASE_URL + BOARDS_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo("Board without default lists");

        boardId = json.get("id");

        Response response1 = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + BOARDS_ENDPOINT + boardId + "/" + LISTS_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath jsonGET = response1.jsonPath();
        List<String> idLists = jsonGET.getList("id");
        assertThat(idLists).hasSize(0);

        deleteBoard();
    }

    @Test
    public void createBoardWithDefaultLists() {
        Response response = given()
                .spec(reqSpec)
                .queryParam("name", "Board with default lists")
                .queryParam("defaultLists", true)
                .when()
                .post(BASE_URL + BOARDS_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("name")).isEqualTo("Board with default lists");

        boardId = json.get("id");

        Response response1 = given()
                .spec(reqSpec)
                .when()
                .get(BASE_URL + BOARDS_ENDPOINT + boardId + "/" + LISTS_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath jsonGET = response1.jsonPath();
        List<String> listNames = jsonGET.getList("name");
        assertThat(listNames).hasSize(3).contains("Do zrobienia", "Zrobione", "W trakcie");

        deleteBoard();
    }
}