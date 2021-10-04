package com.saltlux.tool.filter.tool.repo;

import com.saltlux.tool.filter.tool.model.BusinessAutomotiveModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessAutomotiveMailRepo extends JpaRepository<BusinessAutomotiveModel, String> {
}
