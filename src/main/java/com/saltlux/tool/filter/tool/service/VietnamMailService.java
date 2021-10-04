package com.saltlux.tool.filter.tool.service;

import com.saltlux.tool.filter.tool.model.AppleModel;
import com.saltlux.tool.filter.tool.model.IdEmail;
import com.saltlux.tool.filter.tool.model.VietnamModel;
import com.saltlux.tool.filter.tool.repo.AppleRepo;
import com.saltlux.tool.filter.tool.repo.EmailRepo;
import com.saltlux.tool.filter.tool.repo.VietnamRepo;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
public class VietnamMailService implements Runnable {

//    private Integer page;
    private Integer size;
    private String type;
    private List<VietnamModel> vietnamModels = new ArrayList<>();

    @Autowired
    private VietnamRepo vietnamRepo;

    @Autowired
    private EmailRepo emailRepo;

    public synchronized void filterVietnamMail() {
        for (int i = 0; i < 5; i++) {
            VietnamMailRun vietnamMailRun = new VietnamMailRun(i);
            Thread thread = new Thread(vietnamMailRun);
            thread.start();
        }
    }

    @Override
    public void run() {
        saveAll(vietnamModels);
    }

    @Transactional
    public void saveAll(List<VietnamModel> invalidModels) {
        if (Objects.isNull(invalidModels) || invalidModels.size() == 0) {
            return;
        }
        vietnamRepo.saveAll(invalidModels);
        System.out.println("Save Success Vietnam Mail");
    }

    @AllArgsConstructor
    class VietnamMailRun implements Runnable {

        private Integer page;

        @Transactional(dontRollbackOn = Exception.class)
        @Override
        public void run() {
            Pageable pageable = PageRequest.of(page, size);
            Page<IdEmail> idEmails = emailRepo.findAllByMemberPrimaryEmailIgnoreCaseContaining(pageable, type);
            System.out.println("Vietnam with page = " + page + " and type = " + ". Thread: " + Thread.currentThread().getName());
            List<VietnamModel> businessEmailModels = AppUtil.mapAll(idEmails.getContent(), VietnamModel.class);
            List<String> ids = businessEmailModels.stream().map(VietnamModel::getMemberId).collect(Collectors.toList());
            vietnamRepo.saveAll(businessEmailModels);
            emailRepo.deleteAllByIdInBatch(ids);
            System.out.println(type + " mail ============== DONE ");
        }
    }
}
