package dev.adress.customerinfo.customer.infrastructure.controller;

import dev.adress.customerinfo.customer.application.CustomerQueryService;
import dev.adress.customerinfo.customer.domain.Customer;
import dev.adress.customerinfo.customer.domain.CustomerNotFoundException;
import dev.adress.customerinfo.shared.infrastructure.http.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class) // Carga solo el controlador
@ContextConfiguration(classes = {CustomerController.class, GlobalExceptionHandler.class}) // Registra el manejador global
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerQueryService customerQueryService;

    @Test
    void getCustomer_validCustomer_returnsCustomerSuccessfully() throws Exception {
        // Arrange
        String documentType = "C";
        String documentNumber = "23445322";
        Customer expectedCustomer = new Customer(
                "Pepe", "Antonio", "Pérez", "Gómez",
                "123456789", "Calle Falsa 123", "Cali");

        when(customerQueryService.getCustomer(documentType, documentNumber))
                .thenReturn(expectedCustomer);

        // Act & Assert
        mockMvc.perform(get("/api/v1/customers")
                        .param("documentType", documentType)
                        .param("documentNumber", documentNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.firstName").value("Pepe"))
                .andExpect(jsonPath("$.data.lastName").value("Pérez"))
                .andExpect(jsonPath("$.data.phone").value("123456789"))
                .andExpect(jsonPath("$.data.city").value("Cali"));
    }

    @Test
    void getCustomer_invalidDocumentType_returnsBadRequest() throws Exception {
        // Arrange
        String documentType = "X";
        String documentNumber = "23445322";

        // Act & Assert
        mockMvc.perform(get("/api/v1/customers")
                        .param("documentType", documentType)
                        .param("documentNumber", documentNumber))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Invalid document type. Only 'C' or 'P' allowed."));
    }

    @Test
    void getCustomer_customerNotFound_returnsNotFound() throws Exception {
        // Arrange
        String documentType = "P";
        String documentNumber = "12345678";
        String expectedMessage = "Customer with document type 'P' and number '12345678' not found.";

        when(customerQueryService.getCustomer(documentType, documentNumber))
                .thenThrow(new CustomerNotFoundException(documentType, documentNumber));

        // Act & Assert
        mockMvc.perform(get("/api/v1/customers")
                        .param("documentType", documentType)
                        .param("documentNumber", documentNumber))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    void getCustomer_unexpectedError_returnsInternalServerError() throws Exception {
        // Arrange
        String documentType = "C";
        String documentNumber = "23445322";

        when(customerQueryService.getCustomer(documentType, documentNumber))
                .thenThrow(new RuntimeException("Unexpected error"));

        // Act & Assert
        mockMvc.perform(get("/api/v1/customers")
                        .param("documentType", documentType)
                        .param("documentNumber", documentNumber))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("An unexpected error occurred. Please try again later."));
    }
}
