package com.carecru.reservation.services;

import com.carecru.reservation.domain.History;
import com.carecru.reservation.domain.Reservation;
import com.carecru.reservation.repository.HistoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
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
        BeanUtils.copyProperties(history, reservation);
        historyRepository.save(history);
    }

    public List<History> listHistoryByDate(Long restaurantId, LocalDate seekDate){
        return historyRepository.findAllByRestaurantAndReservedDate(restaurantId, seekDate);
    }
}
