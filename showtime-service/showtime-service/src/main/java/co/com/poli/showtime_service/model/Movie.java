package co.com.poli.showtime_service.model;

import lombok.Data;
@Data
public class Movie {

    private Long id;
    private String title;
    private String director;
    private int rating;
}
