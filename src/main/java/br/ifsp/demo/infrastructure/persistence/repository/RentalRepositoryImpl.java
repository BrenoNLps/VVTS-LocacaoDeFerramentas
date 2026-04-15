package br.ifsp.demo.infrastructure.persistence.repository;

import br.ifsp.demo.domain.model.Rental;
import br.ifsp.demo.domain.repository.RentalRepository;
import br.ifsp.demo.infrastructure.persistence.jpa.RentalJpaRepository;
import br.ifsp.demo.infrastructure.persistence.mapper.RentalMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class RentalRepositoryImpl implements RentalRepository {

    private final RentalJpaRepository jpaRepository;

    @Override
    public void save(Rental rental) {
        jpaRepository.save(RentalMapper.toJpa(rental));
    }

    @Override
    public Rental findById(String id) {
        return jpaRepository.findById(id)
                .map(RentalMapper::toDomain)
                .orElse(null);
    }
}
