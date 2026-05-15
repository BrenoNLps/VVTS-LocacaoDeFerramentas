package br.ifsp.demo.infrastructure.persistence.repository;

import br.ifsp.demo.domain.model.Customer;
import br.ifsp.demo.domain.repository.CustomerRepository;
import br.ifsp.demo.infrastructure.persistence.entity.CustomerJpaEntity;
import br.ifsp.demo.infrastructure.persistence.jpa.CustomerJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerJpaRepository jpaRepository;

    @Override
    public void save(Customer customer) {
        jpaRepository.save(new CustomerJpaEntity(customer.getId(), customer.getName()));
    }

    @Override
    public Customer findById(String id) {
        return jpaRepository.findById(id)
                .map(e -> new Customer(e.getId(), e.getName()))
                .orElse(null);
    }

    @Override
    public List<Customer> findAll() {
        return jpaRepository.findAll().stream()
                .map(e -> new Customer(e.getId(), e.getName()))
                .toList();
    }
}
