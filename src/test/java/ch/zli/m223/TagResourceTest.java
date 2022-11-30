package ch.zli.m223;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.CoreMatchers.endsWith;
import org.junit.jupiter.api.Test;
import ch.zli.m223.controller.TagController;
import ch.zli.m223.model.Tag;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestHTTPEndpoint(TagController.class)
public class TagResourceTest {

    @Test
    public void testIndexEndpoint() {
        given().when().get().then().statusCode(200).body(startsWith("[")).and().body(endsWith("]"));
    }

    @Test
    public void testPostEndpoint() {
        var payload = new Tag("Test");

        given().when().contentType(ContentType.JSON).body(payload).post().then().statusCode(200);
    }

    @Test
    public void testPutEndpoint() {
        var payload = new Tag("Test");

        given().when().contentType(ContentType.JSON).body(payload).post();
        payload.setTitle("Test2");
        given().when().contentType(ContentType.JSON).body(payload).put("/1").then().statusCode(200);
    }

    @Test
    public void testDeleteEndpoint() {
        var payload = new Tag("Test");

        given().when().contentType(ContentType.JSON).body(payload).post();
        given().when().delete("/1").then().statusCode(204);
    }

}
