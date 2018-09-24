package com.carecru.reservation.controllers.api;

import com.carecru.reservation.domain.History;
import com.carecru.reservation.domain.Reservation;
import com.carecru.reservation.domain.ReservationStatus;
import com.carecru.reservation.domain.response.ReservationResponse;
import com.carecru.reservation.services.HistoryService;
import com.carecru.reservation.services.ReservationService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.util.UriTemplate;

import java.time.LocalDate;
import java.util.List;

@EnableAutoConfiguration
@RestController
@Api(description = "Reservation")
@CrossOrigin
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private HistoryService historyService;

    @PostMapping(path = ApiResource.API_VERSION+"restaurants/{id}/time-slots")
    public ResponseEntity<List<Reservation>> createTimeSlots(@PathVariable Long id){

        reservationService.createTimeSlots(id);

        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PostMapping(path = ApiResource.API_VERSION+"restaurants/{id}/reservation")
    public ResponseEntity<Reservation> createReservation(@PathVariable Long id, @RequestBody Reservation reservation){

        Reservation reservationDB = reservationService.createReservation(id, reservation);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(new UriTemplate(ApiResource.API_VERSION+"reservation/{id}").expand(reservationDB.getReservationId()));

        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @GetMapping(path = ApiResource.API_VERSION+"restaurants/{id}/time-slots")
    public @ResponseBody List<Reservation> findAvailableTimesByRestaurant(@PathVariable Long id) {
        return reservationService.findAvailableTimesByRestaurant(id);
    }

    @DeleteMapping(path = ApiResource.API_VERSION+"restaurants/{id}/reservation")
    public ResponseEntity <Void> deleteReservation(@PathVariable Long id) {
        reservationService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = ApiResource.API_VERSION+"restaurants/{id}/reservation/reschedule")
    public ResponseEntity<ReservationResponse> updateReservation(@PathVariable Long id,
                                                                 @RequestParam(value="reservationId") Long reservationId){

        Reservation reservation = reservationService.findByRestaurantId(reservationId, id);
        if (reservation == null)
            return new ResponseEntity<>(new ReservationResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
        reservation.setStatus(ReservationStatus.BUSY);
        reservation = reservationService.save(reservation);
        ReservationResponse  response = conversionService.convert(reservation, ReservationResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = ApiResource.API_VERSION+"restaurants/{id}/history/")
    public @ResponseBody List<History> listHistory(@PathVariable Long id,
                       @RequestParam(value = "desiredDate") LocalDate desiredDate) {
        return historyService.listHistoryByDate(id, desiredDate);
    }

}
