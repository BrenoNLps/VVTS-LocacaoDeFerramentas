package br.ifsp.demo.controller;

import br.ifsp.demo.application.service.*;
import br.ifsp.demo.controller.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/rentals")
@AllArgsConstructor
@Tag(name = "Rentals")
public class RentalController {
    private final RegisterRental registerRental;
    private final FinalizeRental finalizeRental;
    private final CancelRental cancelRental;
    private final QueryRentalValue queryRentalValue;

    @Operation(summary = "Register a new rental")
    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegisterRentalRequest request) {
        String rentalId = registerRental.execute(
                request.customerId(),
                request.toolIds(),
                request.startDate(),
                request.guaranteeType(),
                request.depositValue(),
                request.documentNumber()
        );
        return ResponseEntity.created(URI.create("/api/v1/rentals/" + rentalId)).body(rentalId);
    }

    @Operation(summary = "Finalize a rental")
    @PutMapping("/{id}/finalize")
    public ResponseEntity<BigDecimal> finalize(@PathVariable String id, @RequestBody FinalizeRentalRequest request) {
        BigDecimal total = finalizeRental.execute(id, request.endDate());
        return ResponseEntity.ok(total);
    }

    @Operation(summary = "Cancel a rental")
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable String id) {
        cancelRental.execute(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Query rental value for a period")
    @PostMapping("/query-value")
    public ResponseEntity<BigDecimal> queryValue(@RequestBody QueryRentalValueRequest request) {
        BigDecimal value = queryRentalValue.execute(
                request.toolIds(),
                request.startDate(),
                request.endDate()
        );
        return ResponseEntity.ok(value);
    }

}
