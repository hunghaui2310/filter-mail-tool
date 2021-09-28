package com.saltlux.tool.filter.tool.repo;

import com.saltlux.tool.filter.tool.model.BusinessEmailModel;
import com.saltlux.tool.filter.tool.model.IdEmail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessEmailRepo extends JpaRepository<BusinessEmailModel, String> {

    @Override
    Page<BusinessEmailModel> findAll(Pageable pageable);

//    @Query("select b from BusinessEmailModel b where b.memberPrimaryEmail like :email")
    Page<BusinessEmailModel> findAllByMemberPrimaryEmailIgnoreCaseContaining(Pageable pageable, String email);

    Page<BusinessEmailModel> findAllByMemberPrimaryEmailStartsWith(Pageable pageable, String email);
}
