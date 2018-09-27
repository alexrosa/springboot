package com.carecru.reservation;

import com.carecru.reservation.controllers.api.ApiResource;
import com.carecru.reservation.domain.Reservation;
import com.carecru.reservation.domain.Restaurant;
import com.carecru.reservation.services.ReservationService;
import com.carecru.reservation.services.RestaurantService;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import static io.restassured.RestAssured.given;
import io.restassured.matcher.ResponseAwareMatcher;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RezzyRauntApplication.class,
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerTest {

    private final Long RESTAURANT_ID = 1L;
    private final Long RESREVATION_ID = 91L;

    private Reservation reservationTest;
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    ReservationService reservationService;
    @LocalServerPort
    private int port;

    @Before
    public void setUp() throws Exception{
        System.out.println("running test...");
        RestAssured.port = port;
        RestAssured.basePath = ApiResource.API_VERSION;
        RestAssured.baseURI = "http://localhost/rezzyraunt/";

        Restaurant rest = restaurantService.findById(RESTAURANT_ID);
        reservationTest = new Reservation();
        reservationTest.setReservedDate(LocalDate.now());
        reservationTest.setDeposit(BigDecimal.valueOf(10));
        reservationTest.setNumberOfCustomers(1);
        reservationTest.setRestaurant(rest);
        reservationTest.setStartTime(LocalTime.now());
        reservationTest.setEndTime(LocalTime.now().plusHours(1));

    }

    @Test
    public void getAvailableSlots() throws Exception{
        given().when()
                .get("restaurants/"+RESTAURANT_ID+"/list-available-slots")
                .then()
                .statusCode(200);
    }

    @Test
    public void createReservation() throws Exception{

        RequestSpecification request = new RequestSpecBuilder().build();
        given().spec(request)
                .contentType(ContentType.JSON)
                .body(reservationTest)
                .post("restaurants/"+RESTAURANT_ID+"/reservation")
                .then()
                .statusCode(201)
                .log()
                .all();
    }


    @After
    public void reservationCheck() throws Exception{
        given().when()
                .put("restaurants/"+RESTAURANT_ID+"/reservation-check/92")
                .then()
                .assertThat().contentType(ContentType.JSON)
                .assertThat().statusCode(404)
                .log()
                .all();
    }

    @Test
    public void cancelReservation() throws Exception{
        given().queryParam("reservationId", RESREVATION_ID)
                .when()
                .put("restaurants/"+RESTAURANT_ID+"/cancel")
                .then()
                .assertThat().contentType(ContentType.TEXT)
                .assertThat().statusCode(204)
                .log()
                .all();


    }

}
