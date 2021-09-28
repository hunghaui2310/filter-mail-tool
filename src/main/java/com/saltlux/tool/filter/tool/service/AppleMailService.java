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
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
public class AppleMailService implements Runnable {

    private Integer page;
    private Integer size;
    private String type;

    @Autowired
    private AppleRepo appleRepo;

    @Autowired
    private EmailRepo emailRepo;

    @Transactional(dontRollbackOn = Exception.class)
    public synchronized void filterAppleMail() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 2; i++) {
            int finalI = i;
            executorService.execute(() -> {
                Pageable pageable = PageRequest.of(finalI, size);
                Page<IdEmail> idEmails = emailRepo.findAllByMemberPrimaryEmailIgnoreCaseContaining(pageable, type);
                System.out.println("Apple with page = " + finalI + " and type = " + type + ". Total = " + idEmails.getTotalElements() + ". Thread: " + Thread.currentThread().getName());
                List<AppleModel> businessEmailModels = AppUtil.mapAll(idEmails.getContent(), AppleModel.class);
                List<String> ids = businessEmailModels.stream().map(AppleModel::getMemberId).collect(Collectors.toList());
                appleRepo.saveAll(businessEmailModels);
                emailRepo.deleteAllByIdInBatch(ids);
                System.out.println(type + " mail ============== DONE ");
//                Thread.currentThread().setDaemon(false);
            });
        }
    }

    @Override
    public void run() {
        try {
            filterAppleMail();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
