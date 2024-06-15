package co.com.poli.showtime_service.persistence.repository;

import co.com.poli.showtime_service.model.Movie;
import co.com.poli.showtime_service.persistence.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime,Long> {

}