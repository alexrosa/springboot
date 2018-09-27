package com.carecru.reservation.services;

import com.carecru.reservation.domain.Restaurant;
import com.carecru.reservation.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService implements IBaseService<Restaurant> {

    @Autowired
    private RestaurantRepository repository;


    @Override
    public Restaurant save(Restaurant obj) {

        return repository.save(obj);
    }

    @Override
    public List<Restaurant> findAll() {
        return (List) repository.findAll();
    }

    @Override
    public Restaurant findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
