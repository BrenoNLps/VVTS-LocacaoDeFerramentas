package br.ifsp.demo.infrastructure.persistence.entity;

import br.ifsp.demo.domain.model.ToolStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tools")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolJpaEntity {

    @Id
    private String id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ToolStatus status;

    private BigDecimal dailyRate;
    private BigDecimal weeklyDailyRate;
    private BigDecimal monthlyDailyRate;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "toolId")
    private List<MaintenanceRecordJpaEntity> maintenanceHistory = new ArrayList<>();
}
