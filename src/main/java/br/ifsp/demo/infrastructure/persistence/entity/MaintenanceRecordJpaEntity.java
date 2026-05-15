package br.ifsp.demo.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "maintenance_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceRecordJpaEntity {

    @Id
    private String id;

    private String toolId;

    private LocalDate sentDate;

    private LocalDate returnDate;

    private boolean closed;
}