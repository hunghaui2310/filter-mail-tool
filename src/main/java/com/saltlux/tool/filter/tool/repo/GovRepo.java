package com.saltlux.tool.filter.tool.repo;

import com.saltlux.tool.filter.tool.model.GovModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GovRepo extends JpaRepository<GovModel, String> {
}
