package com.carecru.reservation.repository;

import com.carecru.reservation.domain.ReservationStatus;
import com.carecru.reservation.domain.Restaurant;
import com.carecru.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservationRepository extends CrudRepository<Reservation, Long> {

    Reservation findReservationByReservedDateAndStartTime(LocalDate date, LocalTime time);

//    @Query("select r from Reservation r where r.start = reservedDate")
//    List<Reservation> findAllByReservedDate(LocalDate reservedDate);

    Reservation findByReservedDate(LocalDate reservedDate);

    Reservation findReservationsByReservationIdAndRestaurantId(Long reservationId, Long restaurantId);

    List<Reservation> findAllByRestaurantAndStatus(Restaurant restaurant, ReservationStatus free);

    @Query("select r from Reservation r where r.reservedDate < :date and r.status = BUSY")
    List<Reservation> findReservationsByHistory(@Param("date") LocalDate date);

}
