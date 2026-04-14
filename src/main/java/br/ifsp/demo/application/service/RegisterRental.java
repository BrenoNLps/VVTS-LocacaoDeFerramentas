package br.ifsp.demo.application.service;

import br.ifsp.demo.domain.repository.CustomerRepository;
import br.ifsp.demo.domain.repository.RentalRepository;
import br.ifsp.demo.domain.repository.ToolRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
        return null;
    }
}
