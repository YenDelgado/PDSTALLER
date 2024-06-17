package co.com.poli.booking_service.controller;

import co.com.poli.booking_service.helper.Response;
import co.com.poli.booking_service.helper.ResponseBuild;
import co.com.poli.booking_service.persistence.entity.Booking;
import co.com.poli.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shoppings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final ResponseBuild build;

    @PostMapping()
    public Response save(@RequestBody Booking booking) {
        bookingService.save(booking);
        return build.success(booking);
    }
    @GetMapping
    public Response findAll(){

        return build.success(bookingService.findAll());
    }
    @GetMapping("/{id}")
    public Response findByID(@PathVariable("id") Long id){

        return build.success(bookingService.findById(id));
    }
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable("id") Long id){
        Booking booking = (Booking) findByID(id).getData();
        if (booking == null) {
            return build.success("La reserva no existe");
        }
        bookingService.delete(booking);
        return build.success(booking);
    }

}
