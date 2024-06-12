package co.com.poli.shoppingservice.clientFeign;

import co.com.poli.shoppingservice.helper.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer")
public interface CustomerClient {

    @GetMapping("/api/v1/poli/customers/nit/{nit}")
    public Response findByNit(@PathVariable("nit") String nit);

    @GetMapping("/api/v1/poli/customers/{id}")
    public Response findByID(@PathVariable("id") Long id);

}
