package com.carecru.reservation;

import com.carecru.reservation.controllers.api.ApiResource;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RezzyRauntApplication.class,
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {

    private final Long restaurantId = 1L;

    @LocalServerPort
    private int port= 5000;

    @Before
    public void setUp() throws Exception{
        RestAssured.port = port;
        RestAssured.basePath = ApiResource.API_VERSION;
        RestAssured.baseURI = "http://localhost";
    }
    @Test
    public void getAvailableSlots() throws Exception{
        System.out.println("running test...");
        given().when()
                .get("restaurants/"+restaurantId+"/time-slots")
                .then()
                .statusCode(200);
    }
}
