package co.com.poli.shoppingservice.controller;

import co.com.poli.shoppingservice.helper.Response;
import co.com.poli.shoppingservice.helper.ResponseBuild;
import co.com.poli.shoppingservice.persistence.entity.Invoice;
import co.com.poli.shoppingservice.service.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shoppings")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final ResponseBuild build;

    @PostMapping()
    public Response save(@RequestBody Invoice invoice) {
        invoiceService.save(invoice);
        return build.success(invoice);
    }

    @DeleteMapping("/{numberInvoice}")
    public Response delete(@PathVariable("numberInvoice") String numberInvoice) {
        Invoice invoice = invoiceService.findByNumberInvoice(numberInvoice);
        if (invoice == null) {
            return build.success("Factura no existe");
        }
        invoiceService.delete(invoice);
        return build.success(invoice);
    }

    @GetMapping("/{numberInvoice}")
      public Response getByNumberInvoice(@PathVariable("numberInvoice") String numberInvoice) {
        Invoice invoice = invoiceService.findByNumberInvoice(numberInvoice);
        if (invoice == null) {
            return build.success("Factura no existe");
        }
        return build.success(invoice);
    }
}
