package com.saltlux.tool.filter.tool.service;

import com.saltlux.tool.filter.tool.model.AppleModel;
import com.saltlux.tool.filter.tool.model.IdEmail;
import com.saltlux.tool.filter.tool.model.InvalidModel;
import com.saltlux.tool.filter.tool.model.YahooMailModel;
import com.saltlux.tool.filter.tool.repo.*;
import com.saltlux.tool.filter.tool.util.AppUtil;
import com.saltlux.tool.filter.tool.util.FileUtil;
import com.saltlux.tool.filter.tool.util.FilterUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.poi.util.StringUtil;
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
public class YahooMailService implements Runnable {

    private Integer size;
    private String type;
    private List<YahooMailModel> yahooMailModels = new ArrayList<>();

    @Autowired
    private EmailRepo emailRepo;

    @Autowired
    private YahooEmailRepo yahooEmailRepo;

    @Deprecated
    @Transactional(dontRollbackOn = Exception.class)
    public void filter(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<IdEmail> idEmails = emailRepo.findAllByMemberPrimaryEmailIgnoreCaseContaining(pageable, type);
        List<YahooMailModel> yahooMailModels = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < idEmails.getTotalPages(); i++) {
            String email = idEmails.getContent().get(i).getMemberPrimaryEmail();
            String memberId = idEmails.getContent().get(i).getMemberId();
            if (!Objects.isNull(email)) {
                if (AppUtil.containIgnoreCase(email, "@yahoo") || AppUtil.containIgnoreCase(email, "@ymail")) {
                    yahooMailModels.add(new YahooMailModel(memberId, email));
                }
            }
            ids.add(memberId);
        }
        yahooEmailRepo.saveAll(yahooMailModels);
        emailRepo.deleteAllByIdInBatch(ids);
        System.out.println("Yahoo mail with concurrent task executor ============== DONE " + Thread.currentThread().getName());
    }

    public synchronized void filterYahooMail() {
        for (int i = 0; i < 5; i++) {
            YahooMailRun yahooMailRun = new YahooMailRun(i);
            Thread thread = new Thread(yahooMailRun);
            thread.start();
        }
    }

    @Override
    public void run() {
        saveAll(yahooMailModels);
    }

    @Transactional
    public void saveAll(List<YahooMailModel> invalidModels) {
        if (Objects.isNull(invalidModels) || invalidModels.size() == 0) {
            return;
        }
        yahooEmailRepo.saveAll(invalidModels);
        System.out.println("Save Success Yahoo Mail");
    }

    @AllArgsConstructor
    class YahooMailRun implements Runnable {

        private Integer page;

        @Transactional(dontRollbackOn = Exception.class)
        @Override
        public void run() {
            try {
                Pageable pageable = PageRequest.of(page, size);
                Page<IdEmail> idEmails = emailRepo.findAllByMemberPrimaryEmailIgnoreCaseContaining(pageable, type);
                System.out.println("Yahoo mail with page = " + page + " and type = " + type + ". Thread: " + Thread.currentThread().getName());
                List<YahooMailModel> businessEmailModels = AppUtil.mapAll(idEmails.getContent(), YahooMailModel.class);
                List<String> ids = businessEmailModels.stream().map(YahooMailModel::getMemberId).collect(Collectors.toList());
                yahooEmailRepo.saveAll(businessEmailModels);
                emailRepo.deleteAllByIdInBatch(ids);
                System.out.println(type + " mail ============== DONE ");
//        Thread.currentThread().setDaemon(false);
            } catch (Exception e) {
                e.getMessage();
            }
        }
    }
}
