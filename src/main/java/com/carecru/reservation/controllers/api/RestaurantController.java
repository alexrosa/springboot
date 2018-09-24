package com.carecru.reservation.controllers.api;

import com.carecru.reservation.domain.Reservation;
import com.carecru.reservation.domain.ReservationStatus;
import com.carecru.reservation.domain.Restaurant;
import com.carecru.reservation.domain.request.RestaurantRequest;
import com.carecru.reservation.domain.response.ReservationResponse;
import com.carecru.reservation.domain.response.RestaurantResponse;
import com.carecru.reservation.services.ReservationService;
import com.carecru.reservation.services.RestaurantService;
import io.swagger.annotations.Api;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriTemplate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@EnableAutoConfiguration
@RestController
@RequestMapping("/api/v1/restaurants")
@Api(description = "Restaurant")
@CrossOrigin
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/register")
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant request){

        Restaurant restaurantDB = restaurantService.save(request);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(new UriTemplate("/api/v1/restaurants/{id}").expand(restaurantDB.getId()));

        return new ResponseEntity<>(restaurantDB, httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping
    public @ResponseBody List<Restaurant> listRestaurants(){
        return restaurantService.findAll();
    }
    
}
