package com.saltlux.tool.filter.tool.repo;

import com.saltlux.tool.filter.tool.model.LinkedinAccountModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkedinAccountRepo extends JpaRepository<LinkedinAccountModel, String> {
}
