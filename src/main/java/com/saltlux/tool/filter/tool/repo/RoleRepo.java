package com.saltlux.tool.filter.tool.repo;

import com.saltlux.tool.filter.tool.model.RoleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<RoleModel, String> {

    Page<RoleModel> findAllByMemberPrimaryEmailIgnoreCaseContaining(Pageable pageable, String email);
}
