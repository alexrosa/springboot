package com.carecru.reservation.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
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
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate reservedDate;

    @NotNull
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime startTime;

    @NotNull
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime endTime;

    @NotNull
    private BigDecimal deposit;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @JsonBackReference("restaurant")
    private Restaurant restaurant;

    private ReservationStatus status;

    private BigDecimal fee;
}
