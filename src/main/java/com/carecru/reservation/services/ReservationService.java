package com.carecru.reservation.services;

import com.carecru.reservation.domain.Reservation;
import com.carecru.reservation.domain.ReservationStatus;
import com.carecru.reservation.domain.Restaurant;
import com.carecru.reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import java.time.LocalTime;

@Service
public class ReservationService implements IBaseService<Reservation>{

    public static final int TOTAL_DAYS = 90;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Override
    public Reservation save(Reservation reservation){
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation findById(Long id){
        return reservationRepository.findById(id).get();
    }

    public Reservation checkAvaialableSlot(LocalDate date, LocalTime time){
        return reservationRepository.findReservationByReservedDateAndStartTime(date, time);
    }

    @Override
    public void delete(Long id){
        reservationRepository.deleteById(id);
    }

    @Override
    public List<Reservation> findAll() {
        return (List) reservationRepository.findAll();
    }

    public Reservation findByRestaurantId(Long reservationId, Long restaurantId){
        return reservationRepository.findReservationByReservationIdAndReservationId(reservationId, restaurantId);
    }

    public Reservation createReservation(Long id, Reservation reservation) {
        Reservation reservationDB = reservationRepository.findByReservedDate(reservation.getReservedDate());

        if (reservationDB == null) return null;

        if (reservationDB.getStatus() == ReservationStatus.BUSY) return null;

        Restaurant restaurant = restaurantService.findById(id);
        reservationDB.setStatus(ReservationStatus.BUSY);
        reservation.setRestaurant(restaurant);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> findAvailableTimesByRestaurant(Long id) {
        Restaurant restaurant = restaurantService.findById(id);

        return reservationRepository.findAllByRestaurantAndStatus(restaurant, ReservationStatus.FREE);

    }

    public void createTimeSlots(Long id) {
        Restaurant restaurant = restaurantService.findById(id);
        Reservation reservation;

        LocalDate reservedDate = LocalDate.now();
        LocalTime startTime = LocalTime.of(12, 0, 0);
        for (int i = 0; i < TOTAL_DAYS; i++) {
            reservation = new Reservation();
            reservation.setRestaurant(restaurant);
            reservation.setReservedDate(reservedDate);
            reservation.setStartTime(startTime);
            reservation.setEndTime(startTime.plusHours(12).minusMinutes(1));
            reservation.setStatus(ReservationStatus.FREE);
            reservation.setDeposit(10);
            reservationRepository.save(reservation);
            reservedDate = reservedDate.plusDays(1);
        }
    }
    
    public List<Reservation> listHistory(LocalDate dateToFind){
        return reservationRepository.findReservationsByHistory(dateToFind);
    }

}
