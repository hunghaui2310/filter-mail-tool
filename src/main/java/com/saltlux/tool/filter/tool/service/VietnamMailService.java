package com.saltlux.tool.filter.tool.service;

import com.saltlux.tool.filter.tool.model.AppleModel;
import com.saltlux.tool.filter.tool.model.IdEmail;
import com.saltlux.tool.filter.tool.model.VietnamModel;
import com.saltlux.tool.filter.tool.repo.AppleRepo;
import com.saltlux.tool.filter.tool.repo.EmailRepo;
import com.saltlux.tool.filter.tool.repo.VietnamRepo;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
public class VietnamMailService implements Runnable {

    private Integer page;
    private Integer size;
    private String type;

    @Autowired
    private VietnamRepo vietnamRepo;

    @Autowired
    private EmailRepo emailRepo;

    @Transactional(dontRollbackOn = Exception.class)
    public void filterVietnamMail() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            executorService.execute(() -> {
                Pageable pageable = PageRequest.of(page, size);
                Page<IdEmail> idEmails = emailRepo.findAllByMemberPrimaryEmailIgnoreCaseContaining(pageable, type);
                System.out.println("Vietnam with page = " + page + " and type = " + type + " = " + idEmails.getTotalElements() + ". Thread: " + Thread.currentThread().getName());
                List<VietnamModel> businessEmailModels = AppUtil.mapAll(idEmails.getContent(), VietnamModel.class);
                List<String> ids = businessEmailModels.stream().map(VietnamModel::getMemberId).collect(Collectors.toList());
                vietnamRepo.saveAll(businessEmailModels);
                emailRepo.deleteAllByIdInBatch(ids);
                System.out.println(type + " mail ============== DONE ");
//             Thread.currentThread().setDaemon(false);
            });
        }
    }

    @Override
    public void run() {
        try {
            filterVietnamMail();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
