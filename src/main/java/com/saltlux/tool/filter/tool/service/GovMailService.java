package com.saltlux.tool.filter.tool.service;

import com.saltlux.tool.filter.tool.model.AppleModel;
import com.saltlux.tool.filter.tool.model.GovModel;
import com.saltlux.tool.filter.tool.model.IdEmail;
import com.saltlux.tool.filter.tool.repo.AppleRepo;
import com.saltlux.tool.filter.tool.repo.EmailRepo;
import com.saltlux.tool.filter.tool.repo.GovRepo;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Setter
@Getter
public class GovMailService implements Runnable {

    //    private Integer page;
    private Integer size;
    private String type;
    private List<GovModel> govModels = new ArrayList<>();

    @Autowired
    private GovRepo govRepo;

    @Autowired
    private EmailRepo emailRepo;

    public synchronized void filterGovMail() {
        for (int i = 0; i < 10; i++) {
            GovMailRun govMailRun = new GovMailRun(i);
            Thread thread = new Thread(govMailRun);
            thread.start();
        }
    }

    @Override
    public void run() {
        saveAll(govModels);
    }

    @Transactional
    public void saveAll(List<GovModel> govModels) {
        if (Objects.isNull(govModels) || govModels.size() == 0) {
            return;
        }
        govRepo.saveAll(govModels);
        System.out.println("Save Success Gov Mail");
    }

    class GovMailRun implements Runnable {

        private Integer page;

        GovMailRun(Integer page) {
            this.page = page;
        }

        @Override
        public void run() {
            try {
                Pageable pageable = PageRequest.of(page, size);
                Page<IdEmail> idEmails = emailRepo.findAllByMemberPrimaryEmailIgnoreCaseContaining(pageable, type);
                System.out.println("Gov with page = " + page + " and type = " + type + " = " + idEmails.getTotalElements() + ". Thread: " + Thread.currentThread().getName());
                List<GovModel> businessEmailModels = AppUtil.mapAll(idEmails.getContent(), GovModel.class);
                List<String> ids = businessEmailModels.stream().map(GovModel::getMemberId).collect(Collectors.toList());
                govRepo.saveAll(businessEmailModels);
                emailRepo.deleteAllByIdInBatch(ids);
                System.out.println("Gov mail ============== DONE " + govRepo.count());
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }
}
