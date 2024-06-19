package co.com.poli.booking_service.controller;

import co.com.poli.booking_service.helper.Response;
import co.com.poli.booking_service.helper.ResponseBuild;
import co.com.poli.booking_service.model.User;
import co.com.poli.booking_service.persistence.entity.Booking;
import co.com.poli.booking_service.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;
    private final ResponseBuild build;
    @PostMapping
    public Response save(@RequestBody Booking booking, BindingResult result){
        if(result.hasErrors()){
            return build.failed(format(result));
        }
        bookingService.save(booking);
        return build.success(booking);
    }
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable("id") Long id){
        Booking booking = bookingService.findById(id);
        if(booking==null){
            return build.success("La reserva no existe");
        }
        bookingService.delete(booking);
        return build.success(booking);
    }

    @GetMapping
    public Response findAll(){
        return build.success(bookingService.findAll());
    }

    @GetMapping("/{id}")
    public Response findByID(@PathVariable("id") Long id){
        Booking booking = bookingService.findById(id);
        if(booking==null){
            return build.success("La reserva no existe");
        }
        return build.success(bookingService.findById(id));
    }
    @GetMapping("/user/{userId}")
    public Response findByUserIdWithUserDetails(@PathVariable("userId") Long userId){
        List<Booking> listBooking = bookingService.findByUserId(userId);
        if (listBooking.isEmpty()) {
            return build.success("El usuario no tiene reservas");
        }
        return build.success(bookingService.findByUserId(userId));
    }

    private List<Map<String,String>> format(BindingResult result){
        return result.getFieldErrors()
                .stream().map(error -> {
                    Map<String,String> err = new HashMap<>();
                    err.put(error.getField(),error.getDefaultMessage());
                    return err;
                }).collect(Collectors.toList());
    }

}

