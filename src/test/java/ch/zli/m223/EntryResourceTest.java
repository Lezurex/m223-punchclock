package ch.zli.m223;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import ch.zli.m223.controller.EntryController;
import ch.zli.m223.model.Entry;
import ch.zli.m223.service.TestTokenService;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestHTTPEndpoint(EntryController.class)
public class EntryResourceTest {

        private TestTokenService tokenService = new TestTokenService();

        @Test
        public void testIndexEndpoint() {
                given().when().header("Authorization", "Bearer " + tokenService.generateToken())
                                .get().then().statusCode(200).body(startsWith("[")).and()
                                .body(endsWith("]"));
        }

        @Test
        public void testPostEndpoint() {
                var payload = new Entry(LocalDateTime.parse("2022-11-30T10:00"),
                                LocalDateTime.parse("2022-11-30T17:00"));

                given().when().header("Authorization", "Bearer " + tokenService.generateToken())
                                .contentType(ContentType.JSON).body(payload).post().then()
                                .statusCode(200).body("checkIn", is("2022-11-30T10:00:00"))
                                .body("checkOut", is("2022-11-30T17:00:00"));
        }

        @Test
        public void testPutEndpoint() {
                var payload = new Entry(LocalDateTime.parse("2022-11-30T10:00"),
                                LocalDateTime.parse("2022-11-30T17:00"));

                given().when().header("Authorization", "Bearer " + tokenService.generateToken())
                                .contentType(ContentType.JSON).body(payload).post();
                payload.setCheckIn(LocalDateTime.parse("2022-11-30T09:00"));
                given().when().header("Authorization", "Bearer " + tokenService.generateToken())
                                .contentType(ContentType.JSON).body(payload).put("/1").then()
                                .statusCode(200).body("checkIn", is("2022-11-30T09:00:00"));
        }

        @Test
        public void testDeleteEndpoint() {
                var payload = new Entry(LocalDateTime.parse("2022-11-30T10:00"),
                                LocalDateTime.parse("2022-11-30T17:00"));

                given().when().header("Authorization", "Bearer " + tokenService.generateToken())
                                .contentType(ContentType.JSON).body(payload).post();
                given().when().header("Authorization", "Bearer " + tokenService.generateToken())
                                .delete("/1").then().statusCode(204);
        }

}
