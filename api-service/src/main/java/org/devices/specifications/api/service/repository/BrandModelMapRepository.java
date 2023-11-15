package org.devices.specifications.api.service.repository;

import org.devices.specifications.api.common.model.sql.BrandModelMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandModelMapRepository extends JpaRepository<BrandModelMap, Integer> {

}
