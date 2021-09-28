package com.saltlux.tool.filter.tool.repo;

import com.saltlux.tool.filter.tool.model.InvalidModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidRepo extends JpaRepository<InvalidModel, String> {
}
