package dev.adress.customerinfo.customer.application;

import dev.adress.customerinfo.customer.domain.Customer;
import dev.adress.customerinfo.customer.domain.CustomerNotFoundException;
import dev.adress.customerinfo.customer.domain.CustomerRepository;
import dev.adress.customerinfo.customer.domain.DocumentType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class CustomerQueryServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerQueryService customerQueryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCustomer_validCustomer_returnsCustomerSuccessfully() {
        // Arrange
        String documentType = "C";
        String documentNumber = "23445322";
        Customer expectedCustomer = new Customer(
                "Pepe", "Antonio", "Pérez", "Gómez",
                "123456789", "Calle Falsa 123", "Cali");

        when(customerRepository.findByDocument(DocumentType.C, documentNumber))
                .thenReturn(Optional.of(expectedCustomer));

        // Act
        Customer actualCustomer = customerQueryService.getCustomer(documentType, documentNumber);

        // Assert
        assertEquals(expectedCustomer, actualCustomer, "El cliente devuelto debe coincidir con el esperado");
    }

    @Test
    void getCustomer_customerNotFound_throwsException() {
        // Arrange
        String documentType = "P";
        String documentNumber = "12345678";
        String expectedErrorMessage = "Customer with document type 'P' and number '12345678' not found.";

        when(customerRepository.findByDocument(DocumentType.P, documentNumber))
                .thenReturn(Optional.empty());

        // Act & Assert
        CustomerNotFoundException exception = assertThrows(
                CustomerNotFoundException.class,
                () -> customerQueryService.getCustomer(documentType, documentNumber),
                "Se esperaba que se lanzara CustomerNotFoundException"
        );

        assertEquals(expectedErrorMessage, exception.getMessage(), "El mensaje de error debe coincidir con el esperado");
    }
}
