package ch.zli.m223;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import ch.zli.m223.controller.EntryController;
import ch.zli.m223.model.Entry;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import java.time.LocalDateTime;

@QuarkusTest
@TestHTTPEndpoint(EntryController.class)
public class EntryResourceTest {

    @Test
    public void testIndexEndpoint() {
        try {
            var payload = new Entry(LocalDateTime.parse("2022-11-30T10:00"),
                    LocalDateTime.parse("2022-11-30T17:00"));
            var mapper = new ObjectMapper();
            var json = mapper.writeValueAsString(payload);

            given().when().contentType(ContentType.JSON).body(json).post().then().statusCode(200)
                    .body(is(json));

            given().when().get().then().statusCode(200);

            payload.setCheckIn(LocalDateTime.parse("2022-11-30T09:00"));
            json = mapper.writeValueAsString(payload);
            given().when().contentType(ContentType.JSON).body(json).put("/entries/1").then()
                    .statusCode(200);

            given().when().delete("/entries/1").then().statusCode(200);
        } catch (Exception ex) {

        }
    }

}
