package com.saltlux.tool.filter.tool.service;

import com.saltlux.tool.filter.tool.model.IdEmail;
import com.saltlux.tool.filter.tool.model.InvalidModel;
import com.saltlux.tool.filter.tool.repo.EmailRepo;
import com.saltlux.tool.filter.tool.repo.InvalidRepo;
import com.saltlux.tool.filter.tool.util.AppUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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
public class InvalidMailService implements Runnable {

    private Integer size;
    private String type;
    private List<InvalidModel> invalidModels = new ArrayList<>();

    @Autowired
    private InvalidRepo invalidRepo;

    @Autowired
    private EmailRepo emailRepo;

    @Override
    public void run() {
        saveAll(invalidModels);
    }

    public synchronized void filterInvalidMail() {
        for (int i = 0; i < 3; i++) {
            InvalidMailRun invalidMailRun = new InvalidMailRun(i);
            Thread thread = new Thread(invalidMailRun);
            thread.start();
        }
    }

    @Transactional
    public void saveAll(List<InvalidModel> invalidModels) {
        if (Objects.isNull(invalidModels) || invalidModels.size() == 0) {
            return;
        }
        invalidRepo.saveAll(invalidModels);
        System.out.println("Save Success Invalid Mail");
    }

    @AllArgsConstructor
    class InvalidMailRun implements Runnable {

        private Integer page;

        @Transactional(dontRollbackOn = Exception.class)
        @Override
        public void run() {
            try {
                Pageable pageable = PageRequest.of(page, size);
                Page<IdEmail> idEmails = emailRepo.findAllByMemberPrimaryEmailIgnoreCaseContaining(pageable, type);
                System.out.println("Invalid with page = " + page + " and type = " + ". Thread: " + Thread.currentThread().getName());
                List<InvalidModel> businessEmailModels = AppUtil.mapAll(idEmails.getContent(), InvalidModel.class);
                List<String> ids = businessEmailModels.stream().map(InvalidModel::getMemberId).collect(Collectors.toList());
                invalidRepo.saveAll(businessEmailModels);
                emailRepo.deleteAllByIdInBatch(ids);
                System.out.println(type + " mail ============== DONE ");
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }
}
