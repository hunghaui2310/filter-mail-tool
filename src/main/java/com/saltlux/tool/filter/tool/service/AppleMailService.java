package com.saltlux.tool.filter.tool.service;

import com.saltlux.tool.filter.tool.model.AppleModel;
import com.saltlux.tool.filter.tool.model.BusinessEmailModel;
import com.saltlux.tool.filter.tool.model.IdEmail;
import com.saltlux.tool.filter.tool.repo.AppleRepo;
import com.saltlux.tool.filter.tool.repo.EmailRepo;
import com.saltlux.tool.filter.tool.util.AppUtil;
import com.saltlux.tool.filter.tool.util.FilterUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
public class AppleMailService implements Runnable, IFilterService {

//    private Integer page;
    private Integer size;
    private String type;
    private List<AppleModel> appleModels = new ArrayList<>();

    @Autowired
    private AppleRepo appleRepo;

    @Autowired
    private EmailRepo emailRepo;

    public synchronized void filterAppleMail() {
        for (int i = 0; i < 5; i++) {
            AppleMailRun appleMailRun = new AppleMailRun(i);
            Thread thread = new Thread(appleMailRun);
            thread.start();
        }
    }

    @Override
    public void run() {
        save(appleModels);
    }

    @Override
    public void filter(List<IdEmail> idEmails) {
        System.out.println("Called to Apple Mail Service " + Thread.currentThread().getName());
        List<AppleModel> appleModels = AppUtil.mapAll(idEmails, AppleModel.class)
                .stream()
                .filter(item -> Objects.nonNull(item.getMemberPrimaryEmail()) && FilterUtil.filterAppleMail(item.getMemberPrimaryEmail()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(List<AppleModel> appleModels) {
        if (Objects.isNull(appleModels) || appleModels.size() == 0) {
            return;
        }
        appleRepo.saveAll(appleModels);
        System.out.println("Save Success Apple Mail");
    }

    public class AppleMailRun implements Runnable {

        private Integer page;

        AppleMailRun(Integer page) {
            this.page = page;
        }

        @Transactional(dontRollbackOn = Exception.class)
        @Override
        public void run() {
            try {
                Pageable pageable = PageRequest.of(page, size);
                Page<IdEmail> idEmails = emailRepo.findAllByMemberPrimaryEmailIgnoreCaseContaining(pageable, type);
                System.out.println("Apple with page = " + page + " and type = " + type + ". Total = " + idEmails.getTotalElements() + ". Thread: " + Thread.currentThread().getName());
                List<AppleModel> businessEmailModels = AppUtil.mapAll(idEmails.getContent(), AppleModel.class);
                List<String> ids = businessEmailModels.stream().map(AppleModel::getMemberId).collect(Collectors.toList());
                appleRepo.saveAll(businessEmailModels);
                emailRepo.deleteAllByIdInBatch(ids);
                System.out.println(type + " mail ============== DONE ");
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }
}
