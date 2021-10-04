package com.saltlux.tool.filter.tool.repo;

import com.saltlux.tool.filter.tool.model.BusinessFashionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessFashionMailRepo extends JpaRepository<BusinessFashionModel, String> {
}
