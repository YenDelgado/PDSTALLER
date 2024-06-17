package co.com.poli.booking_service.service;

import co.com.poli.booking_service.clientFeign.MovieClient;
import co.com.poli.booking_service.model.Movie;
import co.com.poli.booking_service.persistence.entity.Booking;
import co.com.poli.booking_service.persistence.entity.BookingMovie;
import co.com.poli.booking_service.persistence.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final MovieClient movieClient;
    private final CircuitBreakerFactory factory;

    @Override
    public void save(Booking booking) {
        bookingRepository.save(booking);
    }

    @Override
    public List<Booking> findAll() {
        List<Booking> listabooking = bookingRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        for(Booking booking : listabooking){
            if(booking.getMovies()!=null){
                List<BookingMovie> items = booking.getMovies().stream()
                        .map(bookingItem -> {
                            bookingItem.setMovie(findByIDMovie(modelMapper,bookingItem.getMovieId()));
                            return bookingItem;
                        }).collect(Collectors.toList());
                booking.setMovies(items);
            }
        }

        return listabooking;
    }

    @Override
    public Booking findById(Long id) {
        //return bookingRepository.findById(id).orElse(null);
        ModelMapper modelMapper = new ModelMapper();
        Booking booking = bookingRepository.findById(id).orElse(null);
        if(booking.getMovies()!=null){
            List<BookingMovie> items = booking.getMovies().stream()
                    .map(bookingItem -> {
                        bookingItem.setMovie(findByIDMovie(modelMapper,bookingItem.getMovieId()));
                        return bookingItem;
                    }).collect(Collectors.toList());
            booking.setMovies(items);
        }
        return booking;
    }
    @Override
    public void delete(Booking booking) {
        bookingRepository.delete(booking);
    }

    public Movie findByIDMovie(ModelMapper modelMapper, Long movieId){
        return factory.create("movie-service")
                .run(()->modelMapper.map(movieClient.findById(movieId).getData(),Movie.class),
                        e -> new Movie());
    }
}