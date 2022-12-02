package ch.zli.m223;

import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.Test;
import ch.zli.m223.controller.SessionController;
import ch.zli.m223.model.Credential;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestHTTPEndpoint(SessionController.class)
public class SessionResourceTest {

  @Test
  public void testLogin() {
    var credentials = new Credential("hans@example.com", "HansFTW123");
    given().when().contentType(ContentType.JSON).body(credentials).post().then().statusCode(200);
  }

}
