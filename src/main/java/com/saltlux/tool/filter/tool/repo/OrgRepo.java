package com.saltlux.tool.filter.tool.repo;

import com.saltlux.tool.filter.tool.model.OrgModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrgRepo extends JpaRepository<OrgModel, String> {
}
