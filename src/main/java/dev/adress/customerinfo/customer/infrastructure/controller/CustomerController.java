package dev.adress.customerinfo.customer.infrastructure.controller;

import dev.adress.customerinfo.customer.application.CustomerQueryService;
import dev.adress.customerinfo.customer.domain.Customer;
import dev.adress.customerinfo.shared.domain.DomainError;
import dev.adress.customerinfo.shared.infrastructure.http.CustomApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerQueryService customerQueryService;

    public CustomerController(CustomerQueryService customerQueryService) {
        this.customerQueryService = customerQueryService;
    }

    @Operation(summary = "Get customer information by document type and number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Customer.class))),
            @ApiResponse(responseCode = "400", description = "Invalid document type",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomApiResponse.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomApiResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected error",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomApiResponse.class)))
    })
    @GetMapping
    public ResponseEntity<CustomApiResponse<?>> getCustomer(
            @Parameter(description = "Type of document (C for Cedula, P for Passport)")
            @RequestParam String documentType,
            @Parameter(description = "Document number")
            @RequestParam String documentNumber) {

        try {
            if (!documentType.matches("[CP]")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(CustomApiResponse.error("Invalid document type. Only 'C' or 'P' allowed.", null, null));
            }

            Customer customer = customerQueryService.getCustomer(documentType, documentNumber);
            return ResponseEntity.ok(CustomApiResponse.success(customer));
        } catch (DomainError e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(CustomApiResponse.error("An unexpected error occurred. Please try again later.", null, null));
        }
    }
}
