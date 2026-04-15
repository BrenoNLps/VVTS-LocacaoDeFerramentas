package br.ifsp.demo.infrastructure.persistence.entity;

import br.ifsp.demo.domain.model.RentalStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;

@Entity
@Table(name = "rentals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalJpaEntity {

    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private RentalStatus status;

    private LocalDate startDate;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "rental_tools",
            joinColumns = @JoinColumn(name = "tool_id"),
            inverseJoinColumns = @JoinColumn(name = "tool_id")
    )
    private List<ToolJpaEntity> tools = new ArrayList<>();
}
