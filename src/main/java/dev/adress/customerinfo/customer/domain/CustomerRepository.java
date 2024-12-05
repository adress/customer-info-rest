package dev.adress.customerinfo.customer.domain;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findByDocument(DocumentType documentType, String documentNumber);
}
