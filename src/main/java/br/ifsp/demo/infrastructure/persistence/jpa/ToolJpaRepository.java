package br.ifsp.demo.infrastructure.persistence.jpa;

import br.ifsp.demo.infrastructure.persistence.entity.ToolJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToolJpaRepository extends JpaRepository<ToolJpaEntity, String> {

}
