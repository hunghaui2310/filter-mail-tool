package com.saltlux.tool.filter.tool.service;

import com.saltlux.tool.filter.tool.model.AppleModel;
import com.saltlux.tool.filter.tool.model.BusinessEmailModel;
import com.saltlux.tool.filter.tool.model.IdEmail;
import com.saltlux.tool.filter.tool.model.RoleModel;
import com.saltlux.tool.filter.tool.repo.AppleRepo;
import com.saltlux.tool.filter.tool.repo.EmailRepo;
import com.saltlux.tool.filter.tool.repo.RoleRepo;
import com.saltlux.tool.filter.tool.util.AppUtil;
import com.saltlux.tool.filter.tool.util.FilterUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
public class RoleMailService implements Runnable {

    private Integer page;
    private Integer size;
    private String type;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private EmailRepo emailRepo;

    @Transactional(dontRollbackOn = Exception.class)
    public void filterRoleMail() throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        Page<IdEmail> idEmails = emailRepo.findAllByMemberPrimaryEmailStartsWith(pageable, type);
//        System.out.println("Role with page = " + page + " and type = " + type + " = " + idEmails.getTotalElements() + ". Thread: " + Thread.currentThread().getName());
        List<RoleModel> businessEmailModels = AppUtil.mapAll(idEmails.getContent(), RoleModel.class);
        List<String> ids = businessEmailModels.stream().map(RoleModel::getMemberId).collect(Collectors.toList());
        roleRepo.saveAll(businessEmailModels);
        emailRepo.deleteAllByIdInBatch(ids);
//        System.out.println("Role mail ============== DONE " + roleRepo.count());
        Thread.currentThread().setDaemon(false);
    }

    @Override
    public void run() {
        try {
            filterRoleMail();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
