package com.saltlux.tool.filter.tool.repo;

import com.saltlux.tool.filter.tool.model.IdEmail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepo extends JpaRepository<IdEmail, String> {

    @Override
    Page<IdEmail> findAll(Pageable pageable);

    Page<IdEmail> findAllByMemberPrimaryEmailIgnoreCaseContaining(Pageable pageable, String email);

//    @Query(value = "SELECT b FROM IdEmail b WHERE b.memberPrimaryEmail like CONCAT(:email,'%')",
//        countQuery = "SELECT COUNT(*) FROM IdEmail b WHERE b.memberPrimaryEmail like CONCAT(:username,'%')")
    Page<IdEmail> findAllByMemberPrimaryEmailStartsWith(Pageable pageable, String email);
}
