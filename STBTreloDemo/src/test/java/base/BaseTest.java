package base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {
    protected static final String BASE_URL = "https://api.trello.com/1/";
    protected static final String BOARDS_ENDPOINT = "boards/";
    protected static final String LISTS_ENDPOINT = "lists/";
    protected static final String CARDS_ENDPOINT = "cards/";
    protected static final String ORG_ENDPOINT = "organizations/";

    protected static final String KEY = "dd73129a36e05cbe677f6aaf7ff85a7b";
    protected static final String TOKEN = "5abb4c3cf5ca58f91a232cd5adc93f3e3d6f4956c0ed31447b823c95fd76e1ce";

    protected static RequestSpecBuilder reqBuilder;
    protected static RequestSpecification reqSpec;

    @BeforeAll
    public static void beforeAll(){
        reqBuilder = new RequestSpecBuilder();
        reqBuilder.addQueryParam("key", KEY);
        reqBuilder.addQueryParam("token", TOKEN);
        reqBuilder.setContentType(ContentType.JSON);

        reqSpec = reqBuilder.build();
    }
}
