import config.VideoGameConfig;
import config.VideoGameEndpoints;
import org.junit.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class FirstTest extends VideoGameConfig {

    @Test
    public void firstTest() {
        given()
                .log().all()
                .when()
                .get("/videogame")
                .then()
                .log().all();
    }

    @Test
    public void firstTestWithEndpoints() {
        get(VideoGameEndpoints.ALL_VIDEO_GAMES)
                .then().log().all();
    }
}
