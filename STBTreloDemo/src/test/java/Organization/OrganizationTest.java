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

public class OrganizationTest extends BaseTest {

    private static String orgId;
    private static Faker faker;
    private static String fakeDisplayName;
    private static String fakeDesc;
    private static String fakeName;
    private static String fakeWebsite;

    @BeforeEach
    public void beforeEach() {
        faker = new Faker();
        fakeDisplayName = faker.dragonBall().character();
        fakeDesc = faker.gameOfThrones().quote();
        fakeName = faker.lorem().word() + 888;
        fakeWebsite = "http://" + faker.internet().url();

    }

    @AfterEach
    public void afterEach() {
        given()
                .spec(reqSpec)
                .when()
                .delete(BASE_URL + ORG_ENDPOINT + orgId)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    public void createNewOrganizationHappyPath() {
        Response response = given()
                .spec(reqSpec)
                .queryParam("displayName", fakeDisplayName)
                .queryParam("desc", fakeDesc)
                .queryParam("name", fakeName)
                .queryParam("website", fakeWebsite)
                .when()
                .post(BASE_URL + ORG_ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertThat(json.getString("displayName")).isEqualTo(fakeDisplayName);
        assertThat(json.getString("desc")).isEqualTo(fakeDesc);
        assertThat(json.getString("name")).isEqualTo(fakeName.toLowerCase(Locale.ROOT));
        assertThat(json.getString("website")).isEqualTo(fakeWebsite);

        orgId = json.getString("id");
    }
}
