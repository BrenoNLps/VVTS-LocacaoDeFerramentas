    package br.ifsp.demo.application.service;

    import br.ifsp.demo.domain.exception.EntityNotFoundException;
    import br.ifsp.demo.domain.exception.InvalidArgumentException;
    import br.ifsp.demo.domain.model.Rental;
    import br.ifsp.demo.domain.repository.RentalRepository;
    import org.springframework.stereotype.Service;

    import java.math.BigDecimal;
    import java.time.LocalDate;
    import java.util.Objects;

    @Service
    public class FinalizeRental {

        private final RentalRepository rentalRepository;

        public FinalizeRental(RentalRepository rentalRepository) {
            this.rentalRepository = rentalRepository;
        }

        public BigDecimal execute(String rentalId, LocalDate endDate) {
            Objects.requireNonNull(rentalId, "rentalId");
            if (rentalId.isBlank()) throw new InvalidArgumentException("rentalId");
            Rental rental = rentalRepository.findById(rentalId);
            if (rental == null) throw new EntityNotFoundException("Rental", rentalId);
            BigDecimal total = rental.finalize(endDate);
            rentalRepository.save(rental);
            return total;
        }
    }
