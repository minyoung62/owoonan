package com.owoonan.owoonan.domain.record.repository;

import com.owoonan.owoonan.domain.record.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long>, RecordRepositoryCustom {
}
