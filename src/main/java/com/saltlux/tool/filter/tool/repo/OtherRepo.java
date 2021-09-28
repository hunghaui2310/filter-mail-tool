package com.saltlux.tool.filter.tool.repo;

import com.saltlux.tool.filter.tool.model.OtherModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherRepo extends JpaRepository<OtherModel, String> {
}
