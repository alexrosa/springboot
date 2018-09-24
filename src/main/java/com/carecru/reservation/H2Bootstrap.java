package com.carecru.reservation;

import com.carecru.reservation.domain.Reservation;
import com.carecru.reservation.domain.Restaurant;
import com.carecru.reservation.repository.ReservationRepository;
import com.carecru.reservation.services.ReservationService;
import com.carecru.reservation.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class H2Bootstrap implements CommandLineRunner {
    @Autowired
    ReservationService reservationService;
    @Autowired
    RestaurantService restaurantService;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("bootingstraping a basic schedule for the restaurants");
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Rezzy Restaurant");
        restaurant = restaurantService.save(restaurant);
        reservationService.createTimeSlots(restaurant.getId());
        List<Reservation> reservationList = reservationService.findAll();

        System.out.println("printing out data: ");
        reservationList.forEach(reservation ->{
            System.out.print(reservation.getReservationId() + " - " +reservation.getReservedDate() +" - "+reservation.getStatus() );
        });

    }
}
