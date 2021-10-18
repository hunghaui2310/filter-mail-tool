package com.saltlux.tool.filter.tool.repo;

import com.saltlux.tool.filter.tool.model.SaltluxModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaltluxRepo extends JpaRepository<SaltluxModel, String> {
}
