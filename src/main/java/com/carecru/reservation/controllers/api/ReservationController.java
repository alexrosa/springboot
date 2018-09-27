package com.carecru.reservation.controllers.api;

import com.carecru.reservation.domain.History;
import com.carecru.reservation.domain.Reservation;
import com.carecru.reservation.services.HistoryService;
import com.carecru.reservation.services.ReservationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.util.UriTemplate;

import javax.ws.rs.Consumes;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@EnableAutoConfiguration
@RestController
@Api(description = "Reservation")
@CrossOrigin
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private HistoryService historyService;

    @PostMapping(path = ApiResource.API_VERSION+"restaurants/{id}/time-slots")
    public ResponseEntity<List<Reservation>> createTimeSlots(@PathVariable Long id){

        reservationService.createTimeSlots(id);

        HttpHeaders httpHeaders = new HttpHeaders();
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PostMapping( path = ApiResource.API_VERSION+"restaurants/{id}/reservation", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createReservation(@PathVariable Long id, @RequestBody  Reservation reservation){

        if (!reservationService.isDepositPaymentOK(reservation))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Invalid value. Do you need to pay $10 per each customer.");

        if (isAValidDate(reservation.getReservedDate()))
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Unfortunately, we couldn't book your scheduled because your date is not valid!");

        Reservation reservationDB = reservationService.createReservation(id, reservation);
        if (reservationDB == null){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("This schedule is already reserved!");

        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(new UriTemplate(ApiResource.API_VERSION+"reservation/{id}").expand(reservationDB.getReservationId()));

        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).body(reservationDB);
    }

    @GetMapping(path = ApiResource.API_VERSION+"restaurants/{id}/list-available-slots")
    public @ResponseBody List<Reservation> findAvailableTimesByRestaurant(@PathVariable Long id) {
        return reservationService.findAvailableTimesByRestaurant(id);
    }

    @GetMapping(path = ApiResource.API_VERSION+"restaurants/{id}/reservation-check")
    public @ResponseBody Object checkReservation(@PathVariable Long id,
                                                      @RequestParam(value="reservationId") Long reservationId) {

        Reservation reservation = reservationService.findByRestaurantId(reservationId, id);
        if (reservation == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Please check your reservation ID!");
        return ResponseEntity.status(HttpStatus.OK).body(reservation);
    }

    @DeleteMapping(path = ApiResource.API_VERSION+"restaurants/{id}/reservation")
    public ResponseEntity <Void> deleteReservation(@PathVariable Long id) {
        reservationService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = ApiResource.API_VERSION+"restaurants/{id}/cancel")
    public ResponseEntity <Object> cancelReservation(@PathVariable Long id,
                                                   @RequestParam(value="reservationId") Long reservationId) throws JsonProcessingException {
        Reservation reservation = reservationService.findByRestaurantId(reservationId, id);
        if (reservation == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Reservation not found");

        BigDecimal refundValue = reservationService.cancelReservation(reservation);

        return ResponseEntity.status(HttpStatus.OK).body(getResponseMessage(refundValue.doubleValue()));
    }

    @PutMapping(path = ApiResource.API_VERSION+"restaurants/{id}/reservation/reschedule")
    public ResponseEntity<Object> updateReservation(@PathVariable Long id,
                                                                 @RequestParam(value="reservationId") Long reservationId) throws JsonProcessingException {
        Reservation reservation = reservationService.findByRestaurantId(reservationId, id);
        if (reservation == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Reservation not found");

        BigDecimal refundValue = reservationService.rescheduleReservation(reservation);

        return ResponseEntity.status(HttpStatus.OK).body(getResponseMessage(refundValue.doubleValue()));
    }

    @GetMapping(path = ApiResource.API_VERSION+"restaurants/{id}/history/")
    public @ResponseBody List<History> listHistory(@PathVariable Long id,
                                                   @RequestParam(value = "desiredDate") LocalDate desiredDate) {
        return historyService.listHistoryByDate(id, desiredDate);
    }

    private String getResponseMessage(double refundValue) throws JsonProcessingException {
        HashMap<String, String> returnMsg = new HashMap<String, String>();
        if (refundValue > 0) {
            returnMsg.put("message", "Your Reservation has canceled and you'll be refunded");
            returnMsg.put("refundValue", "$" + refundValue);
        }else {
            returnMsg.put("message", "Your Reservation has canceled and you'll not be refunded");
            returnMsg.put("refundValue", "$0.00");
        }
        return new ObjectMapper().writeValueAsString(returnMsg);

    }

    private boolean isAValidDate(LocalDate desired){
        LocalDate now = LocalDate.now();
        return desired.isBefore(now);
    }

}
