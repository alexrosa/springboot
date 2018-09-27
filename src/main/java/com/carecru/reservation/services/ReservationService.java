package com.carecru.reservation.services;

import com.carecru.reservation.domain.Reservation;
import com.carecru.reservation.domain.ReservationStatus;
import com.carecru.reservation.domain.Restaurant;
import com.carecru.reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.*;
import java.util.List;

@Service
public class ReservationService implements IBaseService<Reservation>{

    private static final int TOTAL_DAYS = 90;
    private static final float COST_DEPOSIT_ONE_PERSON = 10;

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
        return reservationRepository.findReservationsByReservationIdAndRestaurantIdAndStatus(reservationId, restaurantId, ReservationStatus.BUSY);
    }

    public Reservation createReservation(Long id, Reservation reservation) {
        Reservation reservationDB = reservationRepository.findByReservedDate(reservation.getReservedDate());

        if ((reservationDB != null) && (reservationDB.getStatus() == ReservationStatus.BUSY)) return null;

        Restaurant restaurant = restaurantService.findById(id);
        reservation.setStatus(ReservationStatus.BUSY);
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
            reservation.setDeposit(BigDecimal.valueOf(10));
            reservationRepository.save(reservation);
            reservedDate = reservedDate.plusDays(1);
        }
    }
    
    public List<Reservation> listHistory(LocalDate dateToFind){
        return reservationRepository.findReservationsByHistory(dateToFind);
    }

    public boolean isDepositPaymentOK(Reservation reservation){
        BigDecimal deposit_amount = BigDecimal.valueOf(reservation.getNumberOfCustomers() * COST_DEPOSIT_ONE_PERSON);
        return (deposit_amount.doubleValue() == reservation.getDeposit().doubleValue());

    }

    public BigDecimal rescheduleReservation(Reservation reservation){
        BigDecimal refundValue = applyingRefundPolicy(reservation);
        reservation.setStatus(ReservationStatus.BUSY);
        reservation.setFee(refundValue);
        reservationRepository.save(reservation);
        return refundValue;
    }

    private boolean isBetween(LocalTime candidate, LocalTime start, LocalTime end) {
        return !candidate.isBefore(start) && !candidate.isAfter(end);
    }
    public BigDecimal cancelReservation(Reservation reservation){
        BigDecimal refundValue = applyingRefundPolicy(reservation);
        reservation.setStatus(ReservationStatus.CANCELED);
        reservation.setFee(refundValue);
        reservationRepository.save(reservation);
        return refundValue;
    }

    private BigDecimal applyingRefundPolicy(Reservation reservation){
        LocalDateTime reservedTime = LocalDateTime.of(reservation.getReservedDate(), reservation.getStartTime());
        Duration duration = Duration.between(LocalDateTime.now(), reservedTime);

        long difference = Math.abs(duration.toHours());
        double refundValue = 0;

        if (difference < 24){
            LocalTime currentTime = LocalTime.now();

            if (isBetween(currentTime, LocalTime.of(12,00), LocalTime.of(23,59))){
                refundValue -= 0.25;
            }else if (isBetween(currentTime, LocalTime.of(2,00), LocalTime.of(11,59))) {
                refundValue -= 0.50;

            }else{
                refundValue -= 0.75;
            }
        }
        reservation.setFee(new BigDecimal(reservation.getDeposit().doubleValue()-refundValue));
        return new BigDecimal(refundValue).setScale(2, BigDecimal.ROUND_CEILING);
    }
}
