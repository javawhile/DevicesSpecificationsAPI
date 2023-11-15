package org.devices.specifications.api.service.repository;

import org.devices.specifications.api.common.model.sql.DetailSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailSpecificationRepository extends JpaRepository<DetailSpecification, Integer> {
}
