import org.junit.Test;

import static io.restassured.RestAssured.given;

public class FirstTest {

    @Test
    public void firstTest() {
        given()
                .log().all()
                .when()
                .get("https://videogamedb.uk/api/videogame")
                .then()
                .log().all();
    }
}
