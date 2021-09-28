package com.saltlux.tool.filter.tool.service;

import com.saltlux.tool.filter.tool.model.AppleModel;
import com.saltlux.tool.filter.tool.model.IdEmail;
import com.saltlux.tool.filter.tool.model.LspModel;
import com.saltlux.tool.filter.tool.repo.AppleRepo;
import com.saltlux.tool.filter.tool.repo.EmailRepo;
import com.saltlux.tool.filter.tool.repo.LspRepo;
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
public class LspMailService implements Runnable {

    private Integer page;
    private Integer size;
    private String type;

    @Autowired
    private LspRepo lspRepo;

    @Autowired
    private EmailRepo emailRepo;

    @Transactional(dontRollbackOn = Exception.class)
    public void filterLspMail() throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        Page<IdEmail> idEmails = emailRepo.findAllByMemberPrimaryEmailIgnoreCaseContaining(pageable, type);
//        System.out.println("Lsp Mail with page = " + page + " and type = " + type + " = " + idEmails.getTotalElements() + ". Thread: " + Thread.currentThread().getName());
        List<LspModel> businessEmailModels = AppUtil.mapAll(idEmails.getContent(), LspModel.class);
        List<String> ids = businessEmailModels.stream().map(LspModel::getMemberId).collect(Collectors.toList());
        long end2 = System.currentTimeMillis();
        lspRepo.saveAll(businessEmailModels);
        emailRepo.deleteAllByIdInBatch(ids);
//        System.out.println("Lsp mail ============== DONE " + lspRepo.count());
        Thread.currentThread().setDaemon(false);
    }

    @Override
    public void run() {
        try {
            filterLspMail();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
