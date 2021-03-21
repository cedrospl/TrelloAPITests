package base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.given;

public class BaseTest {
    protected static final String BASE_URL = "https://api.trello.com/1/";
    protected static final String BOARDS_ENDPOINT = "boards/";
    protected static final String LISTS_ENDPOINT = "lists/";
    protected static final String CARDS_ENDPOINT = "cards/";
    protected static final String ORG_ENDPOINT = "organizations/";

    protected static final String KEY = "YOUR KEY";
    protected static final String TOKEN = "YOUR TOKEN";

    protected static RequestSpecBuilder reqBuilder;
    protected static RequestSpecification reqSpec;


    @BeforeAll
    public static void beforeAll() {
        reqBuilder = new RequestSpecBuilder();
        reqBuilder.addQueryParam("key", KEY);
        reqBuilder.addQueryParam("token", TOKEN);
        reqBuilder.setContentType(ContentType.JSON);

        reqSpec = reqBuilder.build();
    }
}
