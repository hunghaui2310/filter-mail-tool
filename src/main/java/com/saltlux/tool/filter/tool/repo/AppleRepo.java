package com.saltlux.tool.filter.tool.repo;

import com.saltlux.tool.filter.tool.model.AppleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppleRepo extends JpaRepository<AppleModel, String> {
}
