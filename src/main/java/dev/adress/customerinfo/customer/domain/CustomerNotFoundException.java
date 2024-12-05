package dev.adress.customerinfo.customer.domain;

import dev.adress.customerinfo.shared.domain.DomainError;

public class CustomerNotFoundException extends DomainError {
    public CustomerNotFoundException(String documentType, String documentNumber) {
        super("customer_not_found",
                String.format("Customer with document type '%s' and number '%s' not found.", documentType, documentNumber));
    }
}
