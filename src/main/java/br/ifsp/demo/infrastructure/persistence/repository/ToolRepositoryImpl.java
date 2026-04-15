package br.ifsp.demo.infrastructure.persistence.repository;

import br.ifsp.demo.domain.model.Tool;
import br.ifsp.demo.domain.repository.ToolRepository;
import br.ifsp.demo.infrastructure.persistence.jpa.ToolJpaRepository;
import br.ifsp.demo.infrastructure.persistence.mapper.ToolMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@AllArgsConstructor
public class ToolRepositoryImpl implements ToolRepository {

    private final ToolJpaRepository jpaRepository;

    @Override
    public void save(Tool tool) {
        jpaRepository.save(ToolMapper.toJpa(tool));
    }

    @Override
    public Tool findById(String id) {
        return jpaRepository.findById(id)
                .map(ToolMapper::toDomain)
                .orElse(null);
    }
}
