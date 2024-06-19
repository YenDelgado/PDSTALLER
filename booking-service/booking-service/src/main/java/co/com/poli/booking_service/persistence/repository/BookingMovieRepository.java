package co.com.poli.booking_service.persistence.repository;

import co.com.poli.booking_service.persistence.entity.BookingMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingMovieRepository extends JpaRepository<BookingMovie,Long> {
}

