package com.saltlux.tool.filter.tool.repo;

import com.saltlux.tool.filter.tool.model.BusinessBankingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessBankingMailRepo extends JpaRepository<BusinessBankingModel, String> {
}
