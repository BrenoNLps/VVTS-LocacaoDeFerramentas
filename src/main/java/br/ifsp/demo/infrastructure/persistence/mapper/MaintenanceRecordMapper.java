package br.ifsp.demo.infrastructure.persistence.mapper;

import br.ifsp.demo.domain.model.MaintenanceRecord;
import br.ifsp.demo.infrastructure.persistence.entity.MaintenanceRecordJpaEntity;

public class MaintenanceRecordMapper {

    private MaintenanceRecordMapper() {}

    public static MaintenanceRecord toDomain(MaintenanceRecordJpaEntity entity) {
        MaintenanceRecord record = new MaintenanceRecord(entity.getSentDate());
        if (entity.isClosed()) record.close(entity.getReturnDate());
        return record;
    }

    public static MaintenanceRecordJpaEntity toJpa(MaintenanceRecord record, String toolId) {
        return new MaintenanceRecordJpaEntity(
                record.getId(),
                toolId,
                record.getSentDate(),
                record.getReturnDate(),
                record.isClosed()
        );
    }
}