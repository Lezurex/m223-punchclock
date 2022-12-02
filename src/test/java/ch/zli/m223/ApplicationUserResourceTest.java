package ch.zli.m223;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import org.junit.jupiter.api.Test;
import ch.zli.m223.controller.ApplicationUserController;
import ch.zli.m223.model.ApplicationUser;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
@TestHTTPEndpoint(ApplicationUserController.class)
@TestSecurity(user = "hans@test.com", roles = "user")
public class ApplicationUserResourceTest {

    @Test
    public void testIndexEndpoint() {
        given().when().get().then().statusCode(200).body(startsWith("[")).and().body(endsWith("]"));
    }

    @Test
    public void testPostEndpoint() {
        var payload = new ApplicationUser("jonas@example.com", "JonasFTW123");

        given().when().contentType(ContentType.JSON).body(payload).post().then().statusCode(200)
                .body("email", is("jonas@example.com"));
    }

    @Test
    public void testPutEndpoint() {
        var payload = new ApplicationUser("jonas@example.com", "JonasFTW123");

        given().when().contentType(ContentType.JSON).body(payload).post();
        payload.setEmail("paul@example.com");
        payload.setPassword("PaulFTW123");
        given().when().contentType(ContentType.JSON).body(payload).put("/3").then().statusCode(200)
                .body("email", is("paul@example.com"));
    }

    @Test
    public void testDeleteEndpoint() {
        given().when().delete("/2").then().statusCode(204);
    }

}
