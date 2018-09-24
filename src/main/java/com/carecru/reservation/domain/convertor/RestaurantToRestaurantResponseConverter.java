package com.carecru.reservation.domain.convertor;

import com.carecru.reservation.domain.Restaurant;
import com.carecru.reservation.domain.response.RestaurantResponse;
import org.springframework.core.convert.converter.Converter;

public class RestaurantToRestaurantResponseConverter implements Converter<Restaurant, RestaurantResponse> {

    @Override
    public RestaurantResponse convert(Restaurant restaurant) {
        RestaurantResponse response = new RestaurantResponse();
        response.setId(restaurant.getId());
        response.setName(restaurant.getName());
        return response;
    }
}

