package co.com.poli.movie_service.controller;

import co.com.poli.movie_service.helper.Response;
import co.com.poli.movie_service.helper.ResponseBuild;
import co.com.poli.movie_service.persistence.entity.Movie;
import co.com.poli.movie_service.service.MovieService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {


    private final MovieService movieService;
    private final ResponseBuild build;
    @PostMapping
    public Response save(@Valid @RequestBody Movie movie, BindingResult result){
        if(result.hasErrors()){
            return build.success(format(result));
        }
        movieService.save(movie);
        return build.success(movie);
    }
    @DeleteMapping("/{id}")
    public Response delete(@PathVariable("id") Long id){
        Movie movie = (Movie) findByID(id).getData();
        if(movie==null){
            return build.success("La película no existe");
        }
        movieService.delete(movie);
        return build.success(movie);
    }

    @GetMapping
    public Response findAll(){
        return build.success(movieService.findAll());
    }

    @GetMapping("/{id}")
    public Response findByID(@PathVariable("id") Long id){
        return build.success(movieService.findById(id));
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
