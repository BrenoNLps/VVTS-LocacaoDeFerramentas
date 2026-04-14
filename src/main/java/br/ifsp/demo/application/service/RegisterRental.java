package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.exception.EntityNotFoundException;
import br.ifsp.demo.domain.model.Customer;
import br.ifsp.demo.domain.model.Rental;
import br.ifsp.demo.domain.model.Tool;
import br.ifsp.demo.domain.model.ToolStatus;
import br.ifsp.demo.domain.repository.CustomerRepository;
import br.ifsp.demo.domain.repository.RentalRepository;
import br.ifsp.demo.domain.repository.ToolRepository;
import br.ifsp.demo.exception.ToolUnavailableException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class RegisterRental {

    private final RentalRepository rentalRepository;
    private final ToolRepository toolRepository;
    private final CustomerRepository customerRepository;

    public RegisterRental(RentalRepository rentalRepository, ToolRepository toolRepository, CustomerRepository customerRepository) {
        this.rentalRepository = rentalRepository;
        this.toolRepository = toolRepository;
        this.customerRepository = customerRepository;
    }

    public String execute(
            String customerId,
            List<String> toolsIds,
            LocalDate startDate,
            String guaranteeType,
            BigDecimal depositValue,
            String documentNumber
    ){
        Customer customer = customerRepository.findById(customerId);
        if (customer == null) throw new EntityNotFoundException("Customer", customerId);
        Tool tool0 = toolRepository.findById(toolsIds.get(0));

        for (String toolId : toolsIds) {
            var tool = toolRepository.findById(toolId);
            if (tool == null) throw new EntityNotFoundException("Tool", toolId);
            if (!tool.isAvailable()) throw new ToolUnavailableException(toolId);
            tool.markAsRented();
        }

        Rental rental = new Rental(UUID.randomUUID().toString());
        rentalRepository.save(rental);
        return rental.getId();
    }
}
