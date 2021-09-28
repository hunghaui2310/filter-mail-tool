package com.saltlux.tool.filter.tool.service;

import com.saltlux.tool.filter.tool.model.AppleModel;
import com.saltlux.tool.filter.tool.model.IdEmail;
import com.saltlux.tool.filter.tool.model.OtherModel;
import com.saltlux.tool.filter.tool.repo.EmailRepo;
import com.saltlux.tool.filter.tool.repo.OtherRepo;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
public class OthersMailService implements Runnable {

    private Integer page;
    private Integer size;
    private String type;

    @Autowired
    private EmailRepo emailRepo;

    @Autowired
    private OtherRepo otherRepo;

    @Transactional(dontRollbackOn = Exception.class)
    public void filterOtherMail() throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        Page<IdEmail> idEmails = emailRepo.findAllByMemberPrimaryEmailIgnoreCaseContaining(pageable, type);
//        System.out.println("Others mail with page = " + page + " and type = " + type + " = " + idEmails.getTotalElements() + ". Thread: " + Thread.currentThread().getName());
        List<OtherModel> businessEmailModels = AppUtil.mapAll(idEmails.getContent(), OtherModel.class);
        List<String> ids = businessEmailModels.stream().map(OtherModel::getMemberId).collect(Collectors.toList());
        otherRepo.saveAll(businessEmailModels);
        emailRepo.deleteAllByIdInBatch(ids);
//        System.out.println("Others mail ============== DONE " + otherRepo.count());
        Thread.currentThread().setDaemon(false);
    }

    @Override
    public void run() {
        try {
            filterOtherMail();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
