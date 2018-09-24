package com.carecru.reservation.repository;

import com.carecru.reservation.domain.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {

    List<History> findAllByRestaurantAndReservedDate(Long restaurantId, LocalDate seekDate);
}
