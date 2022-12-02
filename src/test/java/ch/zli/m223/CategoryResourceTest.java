package ch.zli.m223;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import org.junit.jupiter.api.Test;
import ch.zli.m223.controller.CategoryController;
import ch.zli.m223.model.Category;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
@TestHTTPEndpoint(CategoryController.class)
@TestSecurity(user = "hans@test.com", roles = "user")
public class CategoryResourceTest {

    @Test
    public void testIndexEndpoint() {
        given().when().get().then().statusCode(200).body(startsWith("[")).and().body(endsWith("]"));
    }

    @Test
    public void testPostEndpoint() {
        var payload = new Category("Test");

        given().when().contentType(ContentType.JSON).body(payload).post().then().statusCode(200)
                .body("title", is("Test"));
    }

    @Test
    public void testPutEndpoint() {
        var payload = new Category("Test");

        given().when().contentType(ContentType.JSON).body(payload).post();
        payload.setTitle("Test2");
        given().when().contentType(ContentType.JSON).body(payload).put("/4").then().statusCode(200)
                .body("title", is("Test2"));
    }

    @Test
    public void testDeleteEndpoint() {
        var payload = new Category("Test");

        given().when().contentType(ContentType.JSON).body(payload).post();
        given().when().delete("/4").then().statusCode(204);
    }

}
