package br.ifsp.demo.infrastructure.persistence.mapper;

import br.ifsp.demo.domain.model.Rental;
import br.ifsp.demo.domain.model.Tool;
import br.ifsp.demo.infrastructure.persistence.entity.RentalJpaEntity;
import br.ifsp.demo.infrastructure.persistence.entity.ToolJpaEntity;

import java.util.List;

public class RentalMapper {

    private RentalMapper(){

    }

    public static Rental toDomain(RentalJpaEntity entity) {
        List<Tool> tools = entity.getTools().stream()
                .map(ToolMapper::toDomain)
                .toList();
        return new Rental(entity.getId(), tools, entity.getStartDate(), entity.getStatus());
    }

    public static RentalJpaEntity toJpa(Rental rental) {
        List<ToolJpaEntity> toolEntities = rental.getTools().stream()
                .map(ToolMapper::toJpa)
                .toList();

        RentalJpaEntity entity = new RentalJpaEntity();
        entity.setId(rental.getId());
        entity.setStatus(rental.getStatus());
        entity.setStartDate(rental.getStartDate());
        entity.setTools(toolEntities);
        return entity;
    }
}
