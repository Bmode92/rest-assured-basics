import config.VideoGameConfig;
import config.VideoGameEndpoints;
import io.restassured.RestAssured;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import objects.VideoGame;
import org.junit.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class VideoGamesTests extends VideoGameConfig {

    String gameBodyJSON = "{\n" +
            "  \"category\": \"Platform\",\n" +
            "  \"name\": \"Mario\",\n" +
            "  \"rating\": \"Mature\",\n" +
            "  \"releaseDate\": \"2012-05-04\",\n" +
            "  \"reviewScore\": 85\n" +
            "}";

    @Test
    public void getGames() {
        given()
        .when()
                .get(VideoGameEndpoints.ALL_VIDEO_GAMES)
        .then();
    }

    @Test
    public void createNewGameByJSON() {

        given()
                .body(gameBodyJSON)
        .when()
                .post(VideoGameEndpoints.ALL_VIDEO_GAMES)
        .then();

    }

    @Test
    public void createNewGameByXML() {
        String gameBodyXml = "<VideoGameRequest>\n" +
                "\t<category>Platform</category>\n" +
                "\t<name>Mario</name>\n" +
                "\t<rating>Mature</rating>\n" +
                "\t<releaseDate>2012-05-04</releaseDate>\n" +
                "\t<reviewScore>85</reviewScore>\n" +
                "</VideoGameRequest>";

        given()
                .body(gameBodyXml)
                .contentType("application/xml")
                .accept("application/xml")
        .when()
                .post(VideoGameEndpoints.ALL_VIDEO_GAMES)
        .then();
    }

    @Test
    public void updateGame() {
        given()
                .body(gameBodyJSON)
        .when()
                .put("videogame/3")
        .then();
    }

    @Test
    public void deleteGame() {
        given()
                .accept("text/plain")
        .when()
                .delete("videogame/8")
        .then();
    }

    @Test
    public void getSingleGame() {
        given()
                .pathParam("videoGameId", 5)
        .when()
                .get(VideoGameEndpoints.SINGLE_VIDEO_GAME)
        .then();
    }

    @Test
    public void videoGameSerializationByJson() {
        VideoGame videoGame = new VideoGame("Shooter", "MyAwesomeGame", "Mature", "2018-04-04", 99);

        given()
                .body(videoGame)
        .when()
                .post(VideoGameEndpoints.ALL_VIDEO_GAMES)
        .then();
    }

    @Test
    public void convertJsonToPojo() {
        Response response =
                given()
                    .pathParam("VideoGameId", 5)
                .when()
                    .get(VideoGameEndpoints.SINGLE_VIDEO_GAME);

        VideoGame videoGame = response.getBody().as(VideoGame.class);
        System.out.println(videoGame.toString());
    }

    @Test
    public void testVideoGameSchemaXml() {
        given()
                .pathParam("videoGameId", 5)
                .accept("application/xml")
        .when()
                .get(VideoGameEndpoints.SINGLE_VIDEO_GAME)
        .then()
                .body(RestAssuredMatchers.matchesXsd("VideoGameXSD.xsd"));
    }

    @Test
    public void testVideoGameSchemaJson() {
        given()
                .pathParam("videoGameId", 5)
        .when()
                .get(VideoGameEndpoints.SINGLE_VIDEO_GAME)
        .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("VideoGameJsonSchema.json"));
    }

    @Test
    public void captureResponseTime() {
        long responseTime = get(VideoGameEndpoints.ALL_VIDEO_GAMES).time();
        System.out.println("Response time in MS: " + responseTime);
    }

    @Test
    public void assertOnResponeTime() {
        get(VideoGameEndpoints.ALL_VIDEO_GAMES).then()
                .time(lessThan(1000L));
    }
}
