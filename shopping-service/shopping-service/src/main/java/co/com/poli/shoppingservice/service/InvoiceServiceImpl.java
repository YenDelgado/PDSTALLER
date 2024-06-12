package co.com.poli.shoppingservice.service;

import co.com.poli.shoppingservice.clientFeign.CustomerClient;
import co.com.poli.shoppingservice.clientFeign.ProductClient;
import co.com.poli.shoppingservice.model.Customer;
import co.com.poli.shoppingservice.model.Product;
import co.com.poli.shoppingservice.persistence.entity.Invoice;
import co.com.poli.shoppingservice.persistence.entity.InvoiceItem;
import co.com.poli.shoppingservice.persistence.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ProductClient productClient;
    private final CustomerClient customerClient;
    private final CircuitBreakerFactory factory;

    @Override
    public void save(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    @Override
    public void delete(Invoice invoice) {
        invoiceRepository.delete(invoice);
    }

    @Override
    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice findById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    @Override
    public Invoice findByNumberInvoice(String numberInvoice) {
        ModelMapper modelMapper = new ModelMapper();
        Invoice invoice = invoiceRepository.findByNumberInvoice(numberInvoice);
        invoice.setCustomer(findByIDCustomer(modelMapper,invoice.getCustomerId()));
        List<InvoiceItem> items = invoice.getItems().stream()
                .map(invoiceItem -> {
                    invoiceItem.setProduct(findByIDProduct(modelMapper,invoiceItem.getProductId()));
                    return invoiceItem;
                }).collect(Collectors.toList());
        invoice.setItems(items);
        return invoice;
    }

    public Customer findByIDCustomer(ModelMapper modelMapper,Long customerId){
        return factory.create("customer-service")
                .run(()->modelMapper.map(customerClient.findByID(customerId).getData(),Customer.class),
                        e -> new Customer());
    }

    public Product findByIDProduct(ModelMapper modelMapper,Long productId){
        return factory.create("product-service")
                .run(()->modelMapper.map(productClient.findById(productId).getData(),Product.class),
                        e -> new Product());
    }
}