package com.carecru.reservation.scheduler;

import com.carecru.reservation.domain.History;
import com.carecru.reservation.domain.Reservation;
import com.carecru.reservation.services.HistoryService;
import com.carecru.reservation.services.ReservationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.AbstractAuditable_;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;


@Component
@EnableScheduling
public class ScheduledTasks {

    @Autowired
    ReservationService reservationService;
    @Autowired
    HistoryService historyService;

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Scheduled(cron = "0 0 3 * * *")
    public void executeDatabaseBackupTask(){
        logger.info("=== running scheduling tasks ===");
        logger.info("=== copying data table Reservation to History ===");
        LocalDate dateNow = LocalDate.now();
        List<Reservation> reservationList = reservationService.listHistory(dateNow);

        reservationList.forEach(reservation -> {
            historyService.copyReservation(reservation);
        });
        logger.info("=== end copy ===");
    }

}
