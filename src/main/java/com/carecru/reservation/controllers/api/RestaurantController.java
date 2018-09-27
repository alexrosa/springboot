package com.carecru.reservation.controllers.api;

import com.carecru.reservation.domain.Restaurant;
import com.carecru.reservation.services.RestaurantService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriTemplate;

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

        Restaurant savedRestaurant = restaurantService.save(request);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(new UriTemplate("/api/v1/restaurants/{id}").expand(savedRestaurant.getId()));

        return new ResponseEntity<>(savedRestaurant, httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping
    public @ResponseBody List<Restaurant> listRestaurants(){
        return restaurantService.findAll();
    }
    
}
