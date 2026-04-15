package br.ifsp.demo.infrastructure.persistence.repository;

import br.ifsp.demo.domain.model.Customer;
import br.ifsp.demo.domain.repository.CustomerRepository;
import br.ifsp.demo.infrastructure.persistence.jpa.CustomerJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerJpaRepository jpaRepository;

    @Override
    public Customer findById(String id) {
        return jpaRepository.findById(id)
                .map(e -> new Customer(e.getId(), e.getName()))
                .orElse(null);
    }

}
