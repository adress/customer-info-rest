package dev.adress.customerinfo.customer.infrastructure.repository;

import dev.adress.customerinfo.customer.domain.CustomerRepository;
import dev.adress.customerinfo.customer.domain.Customer;
import dev.adress.customerinfo.customer.domain.DocumentType;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryCustomerRepository implements CustomerRepository {

    private final Map<String, Customer> data = new HashMap<>();

    public InMemoryCustomerRepository() {
        data.put("C-23445322", new Customer(
                "Pepe", "Antonio", "Pérez", "Gómez",
                "123456789", "Calle Falsa 123", "Cali"
        ));
    }

    @Override
    public Optional<Customer> findByDocument(DocumentType documentType, String documentNumber) {
        return Optional.ofNullable(data.get(documentType.name() + "-" + documentNumber));
    }
}
