package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.model.Rental;
import br.ifsp.demo.domain.model.ToolStatus;
import br.ifsp.demo.domain.repository.CustomerRepository;
import br.ifsp.demo.domain.repository.RentalRepository;
import br.ifsp.demo.domain.repository.ToolRepository;

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
        customerRepository.findById(customerId);
        var tool = toolRepository.findById(toolsIds.get(0));
        tool.markAsRented();
        Rental rental = new Rental(UUID.randomUUID().toString());
        rentalRepository.save(rental);
        return rental.getId();
    }
}
