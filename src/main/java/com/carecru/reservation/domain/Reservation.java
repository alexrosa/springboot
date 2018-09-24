package com.carecru.reservation.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long reservationId;

    private Integer numberOfCustomers;

    @NotNull
    private LocalDate reservedDate;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @NotNull
    private float deposit;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private ReservationStatus status;

}
