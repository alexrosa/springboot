package com.carecru.reservation.domain.convertor;

import com.carecru.reservation.domain.Reservation;
import com.carecru.reservation.domain.request.ReservationRequest;
import org.springframework.core.convert.converter.Converter;

public class ReservationRequestToReservationConverter implements Converter<ReservationRequest, Reservation> {

    @Override
    public Reservation convert(ReservationRequest source){
        Reservation response = new Reservation();
        response.setNumberOfCustomers(source.getNumberOfCustomers());
        response.setDeposit(source.getDeposit());
        response.setReservedDate(source.getDesiredDate());
        response.setStartTime(source.getDesiredTime());
        response.setReservationId(source.getId());
        response.setNumberOfCustomers(source.getNumberOfCustomers());
        return response;
    }

}
