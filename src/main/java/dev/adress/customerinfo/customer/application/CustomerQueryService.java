package dev.adress.customerinfo.customer.application;

import dev.adress.customerinfo.customer.domain.Customer;
import dev.adress.customerinfo.customer.domain.CustomerNotFoundException;
import dev.adress.customerinfo.customer.domain.CustomerRepository;
import dev.adress.customerinfo.customer.domain.DocumentType;
import org.springframework.stereotype.Service;

@Service
public class CustomerQueryService {
    private final CustomerRepository customerRepository;

    public CustomerQueryService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomer(String documentType, String documentNumber) {
        DocumentType domainDocumentType = DocumentType.valueOf(documentType);
        return customerRepository.findByDocument(domainDocumentType, documentNumber)
                .orElseThrow(() -> new CustomerNotFoundException(documentType, documentNumber));
    }
}
