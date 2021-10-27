package com.saltlux.tool.filter.tool.repo;

import com.saltlux.tool.filter.tool.model.TamModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TamRepo extends JpaRepository<TamModel, String> {
}
