package com.saltlux.tool.filter.tool.repo;

import com.saltlux.tool.filter.tool.model.GmailModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GmailRepo extends JpaRepository<GmailModel, String> {
}
