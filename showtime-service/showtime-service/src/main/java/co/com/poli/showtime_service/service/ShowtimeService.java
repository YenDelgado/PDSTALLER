package co.com.poli.showtime_service.service;

import co.com.poli.showtime_service.persistence.entity.Showtime;
import java.util.List;

public interface ShowtimeService {

    void save(Showtime showtime);
    List<Showtime> findAll();
    Showtime findById(Long id);
    Showtime updateShowtime(Long id, Showtime showtime);

}
