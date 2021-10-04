package com.saltlux.tool.filter.tool.service;

import com.saltlux.tool.filter.tool.model.*;
import com.saltlux.tool.filter.tool.repo.AppleRepo;
import com.saltlux.tool.filter.tool.repo.EmailRepo;
import com.saltlux.tool.filter.tool.repo.RoleRepo;
import com.saltlux.tool.filter.tool.util.AppUtil;
import com.saltlux.tool.filter.tool.util.FilterUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
public class RoleMailService implements Runnable {

//    private Integer page;
    private Integer size;
    private String type;
    private List<RoleModel> roleModels = new ArrayList<>();

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private EmailRepo emailRepo;

    public synchronized void filterRoleMail() {
        for (int i = 0; i < 2; i++) {
            RoleMailRun roleMailRun = new RoleMailRun(i);
            Thread thread = new Thread(roleMailRun);
            thread.start();
        }
    }

    @Override
    public void run() {
        saveAll(roleModels);
    }

    @Transactional
    public void saveAll(List<RoleModel> invalidModels) {
        if (Objects.isNull(invalidModels) || invalidModels.size() == 0) {
            return;
        }
        roleRepo.saveAll(invalidModels);
        System.out.println("Save Success Role Mail");
    }

    @AllArgsConstructor
    class RoleMailRun implements Runnable {

        private Integer page;

        @Transactional(dontRollbackOn = Exception.class)
        @Override
        public void run() {
            try {
                Pageable pageable = PageRequest.of(page, size);
                Page<IdEmail> idEmails = emailRepo.findAllByMemberPrimaryEmailStartsWith(pageable, type);
                System.out.println("Role with page = " + page + " and type = " + ". Thread: " + Thread.currentThread().getName());
                List<RoleModel> businessEmailModels = AppUtil.mapAll(idEmails.getContent(), RoleModel.class);
                List<String> ids = businessEmailModels.stream().map(RoleModel::getMemberId).collect(Collectors.toList());
                roleRepo.saveAll(businessEmailModels);
                emailRepo.deleteAllByIdInBatch(ids);
                System.out.println(type + " mail ============== DONE ");
                Thread.currentThread().setDaemon(false);
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }
}
