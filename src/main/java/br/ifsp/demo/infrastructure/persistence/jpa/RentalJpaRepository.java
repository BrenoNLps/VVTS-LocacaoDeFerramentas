package br.ifsp.demo.infrastructure.persistence.jpa;

import br.ifsp.demo.infrastructure.persistence.entity.RentalJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalJpaRepository extends JpaRepository<RentalJpaEntity, String> {

}
