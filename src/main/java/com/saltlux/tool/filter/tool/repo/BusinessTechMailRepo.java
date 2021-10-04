package com.saltlux.tool.filter.tool.repo;

import com.saltlux.tool.filter.tool.model.BusinessTechModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessTechMailRepo extends JpaRepository<BusinessTechModel, String> {
}
