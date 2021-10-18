package com.saltlux.tool.filter.tool.repo;

import com.saltlux.tool.filter.tool.model.EduModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EduRepo extends JpaRepository<EduModel, String> {
}
