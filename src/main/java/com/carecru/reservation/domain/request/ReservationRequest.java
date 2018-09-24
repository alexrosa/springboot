package com.carecru.reservation.domain.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {

    private Long id;
    private Integer numberOfCustomers;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate desiredDate;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime desiredTime;
    private float deposit;

    public ReservationRequest(){
    }

    public ReservationRequest(Long id, Integer numberOfCustomers, LocalDate desiredDate, LocalTime desiredTime, float deposit) {
        this.id = id;
        this.numberOfCustomers = numberOfCustomers;
        this.desiredDate = desiredDate;
        this.desiredTime = desiredTime;
        this.deposit = deposit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumberOfCustomers() {
        return numberOfCustomers;
    }

    public void setNumberOfCustomers(Integer numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
    }

    public LocalDate getDesiredDate() {
        return desiredDate;
    }

    public void setDesiredDate(LocalDate desiredDate) {
        this.desiredDate = desiredDate;
    }

    public LocalTime getDesiredTime() {
        return desiredTime;
    }

    public void setDesiredTime(LocalTime desiredTime) {
        this.desiredTime = desiredTime;
    }

    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }
}
