package co.com.poli.booking_service.clientFeign;


import co.com.poli.booking_service.helper.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "movie")
public interface MovieClient {

    @GetMapping("/api/v1/poli/movies/{id}")
    public Response findById(@PathVariable("id") Long id);
}
