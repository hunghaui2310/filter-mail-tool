package com.saltlux.tool.filter.tool.service;

import com.saltlux.tool.filter.tool.repo.EmailRepo;
import com.saltlux.tool.filter.tool.repo.GmailRepo;
import com.saltlux.tool.filter.tool.model.GmailModel;
import com.saltlux.tool.filter.tool.model.IdEmail;
import com.saltlux.tool.filter.tool.util.AppUtil;
import com.saltlux.tool.filter.tool.util.FilterUtil;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
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
public class EmailService implements Runnable {

    private Integer page;
    private Integer size;
    private String type;

    @Autowired
    private EmailRepo emailRepo;

    @Autowired
    private GmailRepo gmailRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(dontRollbackOn = Exception.class)
    public void filterGmail() {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            executorService.execute(() -> {
                Pageable pageable = PageRequest.of(finalI, size);
                Page<IdEmail> idEmails = emailRepo.findAllByMemberPrimaryEmailIgnoreCaseContaining(pageable, type);
                System.out.println("Gmail with page = " + finalI + " and type = " + type + " = " + idEmails.getTotalElements() + ". Thread: " + Thread.currentThread().getName());
                List<GmailModel> businessEmailModels = AppUtil.mapAll(idEmails.getContent(), GmailModel.class);
                List<String> ids = businessEmailModels.stream().map(GmailModel::getMemberId).collect(Collectors.toList());
                gmailRepo.saveAll(businessEmailModels);
                emailRepo.deleteAllByIdInBatch(ids);
                System.out.println(type + " ============== DONE " + gmailRepo.count());
//            Thread.currentThread().setDaemon(false);
            });
        }
    }

    @Override
    public void run() {
        filterGmail();
    }
}
