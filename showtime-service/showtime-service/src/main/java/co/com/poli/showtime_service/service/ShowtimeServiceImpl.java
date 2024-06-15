package co.com.poli.showtime_service.service;

import co.com.poli.showtime_service.clientFeign.MovieClient;
import co.com.poli.showtime_service.model.Movie;
import co.com.poli.showtime_service.persistence.entity.Showtime;
import co.com.poli.showtime_service.persistence.entity.ShowtimeMovie;
import co.com.poli.showtime_service.persistence.repository.ShowtimeRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowtimeServiceImpl implements ShowtimeService {

    private final ShowtimeRepository showtimeRepository;
    private final MovieClient movieClient;
    private final CircuitBreakerFactory factory;

    @Override
    public void save(Showtime showtime) {
        showtimeRepository.save(showtime);
    }

    @Override
    public List<Showtime> findAll() {
        List<Showtime> listaShowtime = showtimeRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        for(Showtime showtime : listaShowtime){
            if(showtime.getMovies()!=null){
                List<ShowtimeMovie> items = showtime.getMovies().stream()
                        .map(showtimeItem -> {
                            showtimeItem.setMovie(findByIDMovie(modelMapper,showtimeItem.getMovieId()));
                            return showtimeItem;
                        }).collect(Collectors.toList());
                showtime.setMovies(items);
            }
        }

        return listaShowtime;
    }

    @Override
    public Showtime findById(Long id) {
        //return showtimeRepository.findById(id).orElse(null);
        ModelMapper modelMapper = new ModelMapper();
        Showtime showtime = showtimeRepository.findById(id).orElse(null);
        if(showtime.getMovies()!=null){
            List<ShowtimeMovie> items = showtime.getMovies().stream()
                    .map(showtimeItem -> {
                        showtimeItem.setMovie(findByIDMovie(modelMapper,showtimeItem.getMovieId()));
                        return showtimeItem;
                    }).collect(Collectors.toList());
            showtime.setMovies(items);
        }
        return showtime;
    }

    @Override
    public Showtime updateShowtime(Long id, Showtime showtime) {
        return showtimeRepository.findById(id)
                .map(showtimeMap -> {
                    showtimeMap.setMovies(showtime.getMovies());
                    showtimeMap.setDate(showtime.getDate());
                    return showtimeRepository.save(showtimeMap);
                })
                .orElseGet(() -> {
                    showtime.setId(id);
                    return showtimeRepository.save(showtime);
                });
    }

    public Movie findByIDMovie(ModelMapper modelMapper,Long movieId){
        return factory.create("movie-service")
                .run(()->modelMapper.map(movieClient.findById(movieId).getData(),Movie.class),
                        e -> new Movie());
    }
}