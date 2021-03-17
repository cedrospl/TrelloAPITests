package Organization;

import base.BaseTest;
import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.*;

public class OrganizationNegativeTest extends BaseTest {

    private static Faker faker;
    private static String fakeDesc;
    private static String fakeName;
    private static String fakeWebsite;

    @BeforeEach
    public void beforeEach(){
        faker = new Faker();
        fakeDesc = faker.gameOfThrones().quote();
        fakeName = faker.lorem().word();
        fakeWebsite = "http://" + faker.internet().url();
    }

    @Test
    public void createOrganizationWithEmptyDisplayName(){
        Response response = given()
                .spec(reqSpec)
                .queryParam("displayName", "")
                .when()
                .post(BASE_URL + ORG_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("message")).isEqualTo("Display Name must be at least 1 character");
        assertThat(json.getString("error")).isEqualTo("ERROR");
    }
    @Test
    public void createOrganizationWithEveryFieldPopulatedExceptDisplayName(){
        Response response = given()
                .spec(reqSpec)
                .queryParam("desc", fakeDesc)
                .queryParam("name", fakeName)
                .queryParam("website", fakeWebsite)
                .when()
                .post(BASE_URL + ORG_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("message")).isEqualTo("Display Name must be at least 1 character");
        assertThat(json.getString("error")).isEqualTo("ERROR");
    }
}
