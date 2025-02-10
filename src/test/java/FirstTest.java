import config.TestConfig;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class FirstTest extends TestConfig {

    @Test
    public void firstTest() {
        given()
                .log().all()
                .when()
                .get("/videogame")
                .then()
                .log().all();
    }
}
