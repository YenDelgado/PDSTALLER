package co.com.poli.booking_service.service;

import co.com.poli.booking_service.clientFeign.MovieClient;
import co.com.poli.booking_service.clientFeign.ShowtimeClient;
import co.com.poli.booking_service.clientFeign.UserClient;
import co.com.poli.booking_service.model.Movie;
import co.com.poli.booking_service.model.Showtime;
import co.com.poli.booking_service.model.User;
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
    private final UserClient userClient;
    private final ShowtimeClient showtimeClient;
    private final CircuitBreakerFactory factory;

    @Override
    public void save(Booking booking) {
        bookingRepository.save(booking);
    }

    @Override
    public void delete(Booking booking) {
        bookingRepository.delete(booking);
    }

    @Override
    public List<Booking> findAll() {
        List<Booking> listaBooking = bookingRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        for(Booking booking : listaBooking){
            booking.setUser(findByIDUser(modelMapper,booking.getUserId()));
            booking.setShowtime(findByIDShowtime(modelMapper,booking.getShowtimeId()));
            if(booking.getMovies()!=null){
                List<BookingMovie> items = booking.getMovies().stream()
                        .map(bookingItem -> {
                            bookingItem.setMovie(findByIDMovie(modelMapper,bookingItem.getMovieId()));
                            return bookingItem;
                        }).collect(Collectors.toList());
                booking.setMovies(items);
            }
        }
        return listaBooking;
    }

    @Override
    public Booking findById(Long id) {
        ModelMapper modelMapper = new ModelMapper();
        Booking booking = bookingRepository.findById(id).orElse(null);
        User user = findByIDUser(modelMapper,booking.getUserId());
        Showtime showtime = findByIDShowtime(modelMapper,booking.getShowtimeId());
        booking.setUser(findByIDUser(modelMapper,booking.getUserId()));
        booking.setShowtime(findByIDShowtime(modelMapper,booking.getShowtimeId()));
        if(booking.getMovies()!=null){
            List<BookingMovie> items = booking.getMovies().stream()
                    .map(showtimeItem -> {
                        showtimeItem.setMovie(findByIDMovie(modelMapper,showtimeItem.getMovieId()));
                        return showtimeItem;
                    }).collect(Collectors.toList());
            booking.setMovies(items);
        }

        return booking;
    }
    @Override
    public List<Booking> findByUserId(Long userId) {
        List<Booking> listaRetorno = bookingRepository.findByUserId(userId);
        ModelMapper modelMapper = new ModelMapper();
        //Booking booking = (Booking) bookingRepository.findByUserID(userId);
        for(Booking booking : listaRetorno){
            booking.setUser(findByIDUser(modelMapper,booking.getUserId()));
            booking.setShowtime(findByIDShowtime(modelMapper,booking.getShowtimeId()));
            if(booking.getMovies()!=null){
                List<BookingMovie> items = booking.getMovies().stream()
                        .map(bookingItem -> {
                            bookingItem.setMovie(findByIDMovie(modelMapper,bookingItem.getMovieId()));
                            return bookingItem;
                        }).collect(Collectors.toList());
                booking.setMovies(items);
            }
        }
        return listaRetorno;
    }
    public User findByIDUser(ModelMapper modelMapper,Long userId){
        return factory.create("user-service")
                .run(()->modelMapper.map(userClient.findById(userId).getData(),User.class),
                        e -> new User());
    }
    public Movie findByIDMovie(ModelMapper modelMapper,Long movieId){
        return factory.create("movie-service")
                .run(()->modelMapper.map(movieClient.findById(movieId).getData(),Movie.class),
                        e -> new Movie());
    }
    public Showtime findByIDShowtime(ModelMapper modelMapper,Long showtimeId){
        return factory.create("showtime-service")
                .run(()->modelMapper.map(showtimeClient.findById(showtimeId).getData(),Showtime.class),
                        e -> new Showtime());
    }

}
