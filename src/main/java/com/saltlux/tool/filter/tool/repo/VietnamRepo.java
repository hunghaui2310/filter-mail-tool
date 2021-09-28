package com.saltlux.tool.filter.tool.repo;

import com.saltlux.tool.filter.tool.model.VietnamModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VietnamRepo extends JpaRepository<VietnamModel, String> {
}
