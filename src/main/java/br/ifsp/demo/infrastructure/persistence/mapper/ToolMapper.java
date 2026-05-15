package br.ifsp.demo.infrastructure.persistence.mapper;

import br.ifsp.demo.domain.model.MaintenanceRecord;
import br.ifsp.demo.domain.model.ProgressivePrices;
import br.ifsp.demo.domain.model.Tool;
import br.ifsp.demo.infrastructure.persistence.entity.MaintenanceRecordJpaEntity;
import br.ifsp.demo.infrastructure.persistence.entity.ToolJpaEntity;

import java.util.List;

public class ToolMapper {

    private ToolMapper(){}

    public static Tool toDomain(ToolJpaEntity entity) {
        Tool tool = new Tool(
                entity.getId(),
                entity.getName(),
                entity.getStatus(),
                new ProgressivePrices(
                        entity.getDailyRate(),
                        entity.getWeeklyDailyRate(),
                        entity.getMonthlyDailyRate()
                )
        );
        entity.getMaintenanceHistory().stream()
                .map(MaintenanceRecordMapper::toDomain)
                .forEach(tool::addMaintenanceRecord);
        return tool;
    }

    public static ToolJpaEntity toJpa(Tool tool) {
        List<MaintenanceRecordJpaEntity> history = tool.getMaintenanceHistory().stream()
                .map(record -> MaintenanceRecordMapper.toJpa(record, tool.getId()))
                .toList();
        ToolJpaEntity entity = new ToolJpaEntity(
                tool.getId(),
                tool.getName(),
                tool.getStatus(),
                tool.getPrices().getDailyRate(),
                tool.getPrices().getWeeklyDailyRate(),
                tool.getPrices().getMonthlyDailyRate(),
                history
        );
        return entity;
    }
}
