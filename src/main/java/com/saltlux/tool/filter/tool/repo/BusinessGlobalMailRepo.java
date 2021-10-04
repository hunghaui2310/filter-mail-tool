package com.saltlux.tool.filter.tool.repo;

import com.saltlux.tool.filter.tool.model.BusinessGlobalModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessGlobalMailRepo extends JpaRepository<BusinessGlobalModel, String> {
}
