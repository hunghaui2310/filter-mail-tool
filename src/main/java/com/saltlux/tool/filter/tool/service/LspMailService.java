package com.saltlux.tool.filter.tool.service;

import com.saltlux.tool.filter.tool.model.AppleModel;
import com.saltlux.tool.filter.tool.model.IdEmail;
import com.saltlux.tool.filter.tool.model.InvalidModel;
import com.saltlux.tool.filter.tool.model.LspModel;
import com.saltlux.tool.filter.tool.repo.AppleRepo;
import com.saltlux.tool.filter.tool.repo.EmailRepo;
import com.saltlux.tool.filter.tool.repo.LspRepo;
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
public class LspMailService implements Runnable {

    private Integer size;
    private String type;
    private List<LspModel> lspModels = new ArrayList<>();

    @Autowired
    private LspRepo lspRepo;

    @Autowired
    private EmailRepo emailRepo;

    public synchronized void filterLspMail() throws Exception {
        for (int i = 0; i < 5; i++) {
            LspMailRun lspMailRun = new LspMailRun(i);
            Thread thread = new Thread(lspMailRun);
            thread.start();
        }
    }

    @Override
    public void run() {
        saveAll(lspModels);
    }

    @Transactional
    public void saveAll(List<LspModel> lspModels) {
        if (Objects.isNull(lspModels) || lspModels.size() == 0) {
            return;
        }
        lspRepo.saveAll(lspModels);
        System.out.println("Save Success Lsp Mail");
    }

    @AllArgsConstructor
    class LspMailRun implements Runnable {

        private Integer page;

        @Transactional(dontRollbackOn = Exception.class)
        @Override
        public void run() {
            Pageable pageable = PageRequest.of(page, size);
            Page<IdEmail> idEmails = emailRepo.findAllByMemberPrimaryEmailIgnoreCaseContaining(pageable, type);
            System.out.println("Lsp Mail with page = " + page + " and type = " + type + " = " + idEmails.getTotalElements() + ". Thread: " + Thread.currentThread().getName());
            List<LspModel> businessEmailModels = AppUtil.mapAll(idEmails.getContent(), LspModel.class);
            List<String> ids = businessEmailModels.stream().map(LspModel::getMemberId).collect(Collectors.toList());
            lspRepo.saveAll(businessEmailModels);
            emailRepo.deleteAllByIdInBatch(ids);
            System.out.println("Lsp mail ============== DONE " + lspRepo.count());
        }
    }
}
