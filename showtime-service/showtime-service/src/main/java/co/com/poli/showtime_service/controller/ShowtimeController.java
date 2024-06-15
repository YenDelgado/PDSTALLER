package co.com.poli.showtime_service.controller;

import co.com.poli.showtime_service.helper.Response;
import co.com.poli.showtime_service.helper.ResponseBuild;
import co.com.poli.showtime_service.persistence.entity.Showtime;
import co.com.poli.showtime_service.service.ShowtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/showtimes")
@RequiredArgsConstructor
public class ShowtimeController {

    private final ShowtimeService showtimeService;
    private final ResponseBuild build;

    @PostMapping()
    public Response save(@RequestBody Showtime showtime) {
        showtimeService.save(showtime);
        return build.success(showtime);
    }
    @PutMapping("/{id}")
    public Response updateMovie(@PathVariable Long id, @RequestBody Showtime showtime) {
        Showtime updateMovie = showtimeService.updateShowtime(id, showtime);
        return build.success(updateMovie);
    }

    @GetMapping
    public Response findAll(){
        return build.success(showtimeService.findAll());
    }

    @GetMapping("/{id}")
      public Response getById(@PathVariable("id") Long id) {
        Showtime showtime = showtimeService.findById(id);
        if (showtime == null) {
            return build.success("Programación no existe");
        }
        return build.success(showtime);
    }
}
