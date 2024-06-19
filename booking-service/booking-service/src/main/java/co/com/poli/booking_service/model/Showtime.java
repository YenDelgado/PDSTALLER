package co.com.poli.booking_service.model;

import co.com.poli.booking_service.persistence.entity.ShowtimeMovie;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Showtime {
    private Long id ;
    private Date date;
    private List<ShowtimeMovie> movies;
}
