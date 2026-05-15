package br.ifsp.demo.infrastructure.persistence.mapper;

import br.ifsp.demo.domain.model.Customer;
import br.ifsp.demo.domain.model.Rental;
import br.ifsp.demo.domain.model.Tool;
import br.ifsp.demo.infrastructure.persistence.entity.CustomerJpaEntity;
import br.ifsp.demo.infrastructure.persistence.entity.RentalJpaEntity;
import br.ifsp.demo.infrastructure.persistence.entity.ToolJpaEntity;

import java.util.List;

public class RentalMapper {

    private RentalMapper() {}

    public static Rental toDomain(RentalJpaEntity entity) {
        List<Tool> tools = entity.getTools().stream()
                .map(ToolMapper::toDomain)
                .toList();

        Customer customer = entity.getCustomer() != null
                ? new Customer(entity.getCustomer().getId(), entity.getCustomer().getName())
                : null;

        return Rental.reconstitute(entity.getId(), tools, entity.getStartDate(), entity.getStatus(), customer, entity.getEndDate());
    }

    public static RentalJpaEntity toJpa(Rental rental) {
        List<ToolJpaEntity> toolEntities = rental.getTools().stream()
                .map(ToolMapper::toJpa)
                .toList();

        CustomerJpaEntity customerEntity = rental.getCustomer() != null
                ? new CustomerJpaEntity(rental.getCustomer().getId(), rental.getCustomer().getName())
                : null;

        RentalJpaEntity entity = new RentalJpaEntity();
        entity.setId(rental.getId());
        entity.setStatus(rental.getStatus());
        entity.setStartDate(rental.getStartDate());
        entity.setEndDate(rental.getEndDate());
        entity.setCustomer(customerEntity);
        entity.setTools(toolEntities);
        return entity;
    }
}