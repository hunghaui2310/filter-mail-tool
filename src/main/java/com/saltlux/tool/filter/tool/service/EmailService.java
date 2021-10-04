package com.saltlux.tool.filter.tool.service;

import com.saltlux.tool.filter.tool.model.BusinessEmailModel;
import com.saltlux.tool.filter.tool.repo.EmailRepo;
import com.saltlux.tool.filter.tool.repo.GmailRepo;
import com.saltlux.tool.filter.tool.model.GmailModel;
import com.saltlux.tool.filter.tool.model.IdEmail;
import com.saltlux.tool.filter.tool.util.AppUtil;
import lombok.AllArgsConstructor;
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
import java.util.stream.Collectors;

@Service
@Getter
@Setter
public class EmailService implements Runnable, IFilterService {

    private Integer page;
    private Integer size;
    private String type;
    private List<GmailModel> gmailModels = new ArrayList<>();

    @Autowired
    private EmailRepo emailRepo;

    @Autowired
    private GmailRepo gmailRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BusinessEmailService businessEmailService;

    public synchronized void filterGmail() {
        for (int i = 0; i < 5; i++) {
            GmailRun gmailRun = new GmailRun(i);
            Thread thread = new Thread(gmailRun);
            thread.start();
        }
    }

    @Override
    public void run() {
        saveAll(gmailModels);
    }

    @Override
    public void filter(List<IdEmail> idEmails) {
        System.out.println("Called to Gmail Service");
    }

    @Transactional
    public void saveAll(List<GmailModel> gmailModels) {
        if (Objects.isNull(gmailModels) || gmailModels.size() == 0) {
            return;
        }
        gmailRepo.saveAll(gmailModels);
        System.out.println("Save Success Gmail");
    }

    @Transactional
    public void saveToBusiness() {
        Pageable pageable = PageRequest.of(0, 10000);
        Page<IdEmail> idEmails = emailRepo.findAll(pageable);
        List<BusinessEmailModel> businessEmailModelList = AppUtil.mapAll(idEmails.getContent(), BusinessEmailModel.class);
        businessEmailService.saveAll(businessEmailModelList);
        emailRepo.deleteAllByIdInBatch(businessEmailModelList.stream().map(BusinessEmailModel::getMemberId).collect(Collectors.toList()));
        System.out.println("Done");
    }

    @AllArgsConstructor
    class GmailRun implements Runnable {

        private Integer page;

        @Transactional(dontRollbackOn = Exception.class)
        @Override
        public void run() {
            try {
                Pageable pageable = PageRequest.of(page, size);
                Page<IdEmail> idEmails = emailRepo.findAllByMemberPrimaryEmailIgnoreCaseContaining(pageable, type);
                System.out.println("Gmail with page = " + page + " and type = " + type + " = " + idEmails.getTotalElements() + ". Thread: " + Thread.currentThread().getName());
                List<GmailModel> businessEmailModels = AppUtil.mapAll(idEmails.getContent(), GmailModel.class);
                List<String> ids = businessEmailModels.stream().map(GmailModel::getMemberId).collect(Collectors.toList());
                gmailRepo.saveAll(businessEmailModels);
                emailRepo.deleteAllByIdInBatch(ids);
                System.out.println(type + " ============== DONE " + gmailRepo.count());
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }
}
