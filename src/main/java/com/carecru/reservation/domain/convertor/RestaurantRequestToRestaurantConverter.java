package com.carecru.reservation.domain.convertor;

import com.carecru.reservation.domain.Restaurant;
import com.carecru.reservation.domain.request.RestaurantRequest;
import org.springframework.core.convert.converter.Converter;

public class RestaurantRequestToRestaurantConverter implements Converter<RestaurantRequest, Restaurant> {

    @Override
    public Restaurant convert(RestaurantRequest request){
        Restaurant restaurant = new Restaurant();

        if (request.getId() != null)
            restaurant.setId(request.getId());
        restaurant.setName(request.getName());
        return restaurant;
    }
}
