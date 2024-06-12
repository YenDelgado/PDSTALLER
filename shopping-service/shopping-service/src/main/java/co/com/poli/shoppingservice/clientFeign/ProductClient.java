package co.com.poli.shoppingservice.clientFeign;

import co.com.poli.shoppingservice.helper.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product")
public interface ProductClient {

    @GetMapping("/api/v1/poli/products/{id}")
    Response findById(@PathVariable("id") Long id);
}
