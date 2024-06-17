package co.com.poli.booking_service.persistence.repository;

import co.com.poli.booking_service.persistence.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

}