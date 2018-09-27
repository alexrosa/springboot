package com.carecru.reservation;

import com.carecru.reservation.domain.Reservation;
import com.carecru.reservation.domain.ReservationStatus;
import com.carecru.reservation.domain.Restaurant;
import com.carecru.reservation.repository.ReservationRepository;
import com.carecru.reservation.repository.RestaurantRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@DataJpaTest
public class ReservationDataTest {

    private final Long RESTAURANT_ID = 1l;
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    private LocalDate currentDate;
    private LocalTime currentTime;
    private Reservation currentReservation;

    @Before
    public void setUp() throws Exception {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("rezzy");
        entityManager.persist(restaurant);

        currentDate = LocalDate.now();
        currentTime = LocalTime.now();
        currentReservation = new Reservation();
        currentReservation.setRestaurant(restaurant);
        currentReservation.setStartTime(currentTime);
        currentReservation.setNumberOfCustomers(1);
        currentReservation.setDeposit(BigDecimal.valueOf(10));
        currentReservation.setReservedDate(currentDate);
        currentReservation.setStatus(ReservationStatus.BUSY);
        currentReservation.setEndTime(currentTime.plusHours(1));
    }

    @Test
    public void findByReservationId(){
        this.entityManager.persist(currentReservation);
        Optional<Reservation> reservationDB = reservationRepository.findById(currentReservation.getReservationId());
        assertNotNull(reservationDB);
    }
    @Test
    public void findByReservedDate(){
        this.entityManager.persist(currentReservation);
        Reservation reservationDB = reservationRepository.findByReservedDate(currentDate);
        assertNotNull(reservationDB);
    }
    @Test
    public void findReservationsByReservationIdAndRestaurantId(){
        this.entityManager.persist(currentReservation);
        Reservation reservationDB = reservationRepository.findReservationsByReservationIdAndRestaurantIdAndStatus(currentReservation.getReservationId(), currentReservation.getRestaurant().getId(), ReservationStatus.BUSY);
        assertNotNull(reservationDB);
    }

}
