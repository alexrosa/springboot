package com.carecru.reservation.services;

import com.carecru.reservation.domain.History;
import com.carecru.reservation.domain.Reservation;
import com.carecru.reservation.repository.HistoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public class HistoryService implements IBaseService<History> {

    @Autowired
    HistoryRepository historyRepository;

    @Override
    public History save(History obj) {
        return historyRepository.save(obj);
    }

    @Override
    public List<History> findAll() {
        return historyRepository.findAll();
    }

    @Override
    public History findById(Long id) {
        return historyRepository.getOne(id);
    }

    @Override
    public void delete(Long id) {
        historyRepository.deleteById(id);
    }

    public void copyReservation(Reservation reservation){
        History history = new History();
        /*
        history.setDeposit(reservation.getDeposit());
        history.setNumberOfCustomers(reservation.getNumberOfCustomers());
        history.setReservedDate(reservation.getReservedDate());
        history.setStatus(reservation.getStatus());
        history.setStartTime(reservation.getStartTime());
        history.setEndTime(reservation.getEndTime());*/
        BeanUtils.copyProperties(reservation, history);
        historyRepository.save(history);
    }

    public List<History> listHistoryByDate(Long restaurantId, LocalDate seekDate){
        return historyRepository.findAllByRestaurantAndReservedDate(restaurantId, seekDate);
    }
}
