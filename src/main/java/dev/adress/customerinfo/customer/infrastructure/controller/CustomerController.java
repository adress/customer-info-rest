package dev.adress.customerinfo.customer.infrastructure.controller;

import dev.adress.customerinfo.customer.application.CustomerQueryService;
import dev.adress.customerinfo.customer.domain.Customer;
import dev.adress.customerinfo.shared.domain.DomainError;
import dev.adress.customerinfo.shared.infrastructure.http.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerQueryService customerQueryService;

    public CustomerController(CustomerQueryService customerQueryService) {
        this.customerQueryService = customerQueryService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getCustomer(
            @RequestParam String documentType,
            @RequestParam String documentNumber) {

        try {
            if (!documentType.matches("[CP]")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.error("Invalid document type. Only 'C' or 'P' allowed.", null, null));
            }

            Customer customer = customerQueryService.getCustomer(documentType, documentNumber);
            return ResponseEntity.ok(ApiResponse.success(customer));
        } catch (DomainError e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("An unexpected error occurred. Please try again later.", null, null));
        }
    }
}
