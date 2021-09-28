package com.saltlux.tool.filter.tool.repo;

import com.saltlux.tool.filter.tool.model.BusinessEmailModel;
import com.saltlux.tool.filter.tool.model.YahooMailModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface YahooEmailRepo extends JpaRepository<YahooMailModel, String> {

    List<YahooMailModel> findByMemberPrimaryEmailIgnoreCaseContaining(String email);
}
