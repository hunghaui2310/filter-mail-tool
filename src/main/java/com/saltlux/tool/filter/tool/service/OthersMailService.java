package com.saltlux.tool.filter.tool.service;

import com.saltlux.tool.filter.tool.model.AppleModel;
import com.saltlux.tool.filter.tool.model.IdEmail;
import com.saltlux.tool.filter.tool.model.InvalidModel;
import com.saltlux.tool.filter.tool.model.OtherModel;
import com.saltlux.tool.filter.tool.repo.EmailRepo;
import com.saltlux.tool.filter.tool.repo.OtherRepo;
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
public class OthersMailService implements Runnable {

//    private Integer page;
    private Integer size;
    private String type;
    private List<OtherModel> otherModels = new ArrayList<>();

    @Autowired
    private EmailRepo emailRepo;

    @Autowired
    private OtherRepo otherRepo;

    public synchronized void filterOtherMail(){
        for (int i = 0; i < 1; i++) {
            OtherMailRun otherMailRun = new OtherMailRun(i);
            Thread thread = new Thread(otherMailRun);
            thread.start();
        }
    }

    @Override
    public void run() {
        saveAll(otherModels);
    }

    @Transactional
    public void saveAll(List<OtherModel> invalidModels) {
        if (Objects.isNull(invalidModels) || invalidModels.size() == 0) {
            return;
        }
        otherRepo.saveAll(invalidModels);
        System.out.println("Save Success Other Mail");
    }

    @AllArgsConstructor
    class OtherMailRun implements Runnable {

        private Integer page;

        @Override
        public void run() {
            try {
                Pageable pageable = PageRequest.of(page, size);
                Page<IdEmail> idEmails = emailRepo.findAllByMemberPrimaryEmailIgnoreCaseContaining(pageable, type);
                System.out.println("Others mail with page = " + page + " and type = " + type + ". Thread: " + Thread.currentThread().getName());
                List<OtherModel> businessEmailModels = AppUtil.mapAll(idEmails.getContent(), OtherModel.class);
                List<String> ids = businessEmailModels.stream().map(OtherModel::getMemberId).collect(Collectors.toList());
                otherRepo.saveAll(businessEmailModels);
                emailRepo.deleteAllByIdInBatch(ids);
                System.out.println(type + " mail ============== DONE ");
//                Thread.currentThread().setDaemon(false);
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }
}
