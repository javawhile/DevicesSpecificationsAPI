package org.devices.specifications.api.service.repository;

import org.devices.specifications.api.common.model.sql.BriefSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BriefSpecificationRepository extends JpaRepository<BriefSpecification, Integer> {
}
