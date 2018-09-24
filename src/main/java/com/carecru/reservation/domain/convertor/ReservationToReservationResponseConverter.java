package com.carecru.reservation.domain.convertor;

import com.carecru.reservation.domain.Reservation;
import com.carecru.reservation.domain.response.ReservationResponse;
import org.springframework.core.convert.converter.Converter;

public class ReservationToReservationResponseConverter implements Converter<Reservation, ReservationResponse> {

    @Override
    public ReservationResponse convert(Reservation source){
        ReservationResponse response = new ReservationResponse();
        response.setDeposit(source.getDeposit());
        response.setNumberOfCustomers(source.getNumberOfCustomers());
        response.setReservationId(source.getReservationId());
        response.setReservedDate(source.getReservedDate());
        response.setReservedTime(source.getStartTime());
        return response;
    }
}
