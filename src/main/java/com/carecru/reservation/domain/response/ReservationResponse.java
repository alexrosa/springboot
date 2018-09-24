package com.carecru.reservation.domain.response;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationResponse {
    private Long reservationId;
    private Integer numberOfCustomers;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate reservedDate;
    @JsonFormat(pattern = "hh:mm")
    private LocalTime reservedTime;
    private float deposit;
    private boolean canceled;


    public Long getReservationId() {
        return reservationId;
    }

    public LocalDate getReservedDate() {
        return reservedDate;
    }

    public void setReservedDate(LocalDate reservedDate) {
        this.reservedDate = reservedDate;
    }

    public LocalTime getReservedTime() {
        return reservedTime;
    }

    public void setReservedTime(LocalTime reservedTime) {
        this.reservedTime = reservedTime;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Integer getNumberOfCustomers() {
        return numberOfCustomers;
    }

    public void setNumberOfCustomers(Integer numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
    }

    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}