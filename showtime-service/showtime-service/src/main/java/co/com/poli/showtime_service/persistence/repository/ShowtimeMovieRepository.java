package co.com.poli.showtime_service.persistence.repository;

import co.com.poli.showtime_service.persistence.entity.ShowtimeMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowtimeMovieRepository extends JpaRepository<ShowtimeMovie,Long> {
}

