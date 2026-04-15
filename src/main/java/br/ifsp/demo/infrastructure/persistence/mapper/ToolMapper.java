package br.ifsp.demo.infrastructure.persistence.mapper;

import br.ifsp.demo.domain.model.ProgressivePrices;
import br.ifsp.demo.domain.model.Tool;
import br.ifsp.demo.infrastructure.persistence.entity.ToolJpaEntity;

public class ToolMapper {

    private ToolMapper(){}

    public static Tool toDomain(ToolJpaEntity entity) {
        return new Tool(
                entity.getId(),
                entity.getName(),
                entity.getStatus(),
                new ProgressivePrices(
                        entity.getDailyRate(),
                        entity.getWeeklyDailyRate(),
                        entity.getMonthlyDailyRate()
                )

        );
    }

    public static ToolJpaEntity toJpa(Tool tool) {
        return new ToolJpaEntity(
                tool.getId(),
                tool.getName(),
                tool.getStatus(),
                tool.getPrices().getDailyRate(),
                tool.getPrices().getWeeklyDailyRate(),
                tool.getPrices().getMonthlyDailyRate()
        );
    }

}
