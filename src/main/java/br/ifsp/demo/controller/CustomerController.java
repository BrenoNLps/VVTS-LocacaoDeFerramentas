package br.ifsp.demo.controller;

import br.ifsp.demo.application.service.CreateCustomer;
import br.ifsp.demo.application.service.ListCustomers;
import br.ifsp.demo.controller.dto.CreateCustomerRequest;
import br.ifsp.demo.controller.dto.CustomerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@AllArgsConstructor
@Tag(name = "Customers")
public class CustomerController {

    private final CreateCustomer createCustomer;
    private final ListCustomers listCustomers;

    @Operation(summary = "List all customers")
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> listAll() {
        List<CustomerResponse> customers = listCustomers.execute().stream()
                .map(CustomerResponse::from)
                .toList();
        return ResponseEntity.ok(customers);
    }

    @Operation(summary = "Create a new customer")
    @PostMapping
    public ResponseEntity<CustomerResponse> create(@RequestBody CreateCustomerRequest request) {
        var customer = createCustomer.execute(request.name());
        return ResponseEntity.created(URI.create("/api/v1/customers/" + customer.getId()))
                .body(CustomerResponse.from(customer));
    }
}