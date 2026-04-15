package br.ifsp.demo.infrastructure.persistence.jpa;

import br.ifsp.demo.infrastructure.persistence.entity.CustomerJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<CustomerJpaEntity, String> {

}
