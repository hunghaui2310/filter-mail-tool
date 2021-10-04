package com.saltlux.tool.filter.tool.controller;

import com.saltlux.tool.filter.tool.model.BusinessEmailModel;
import com.saltlux.tool.filter.tool.model.IdEmail;
import com.saltlux.tool.filter.tool.repo.BusinessEmailRepo;
import com.saltlux.tool.filter.tool.repo.EmailRepo;
import com.saltlux.tool.filter.tool.service.*;
import com.saltlux.tool.filter.tool.util.AppUtil;
import com.saltlux.tool.filter.tool.util.FilterConstraints;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private BusinessEmailService businessEmailService;

    @Autowired
    private EmailRepo emailRepo;

    @Autowired
    private BusinessEmailRepo businessEmailRepo;

    @Autowired
    private ApplicationContext applicationContext;

    private final Integer SIZE = 5000;

    @GetMapping("/apple-mail")
    public void filterAppleMail() {
        System.out.println("Staring filter apple mail");
        for (int i = 0; i < FilterConstraints.APPLES.length; i++) {
            AppleMailService businessEmailService = (AppleMailService) applicationContext.getBean("appleMailService");
            businessEmailService.setSize(SIZE);
            businessEmailService.setType(FilterConstraints.APPLES[i]);
            Thread thread = new Thread(businessEmailService);
            thread.start();
        }
    }

    @GetMapping("/business")
    public void filterBusinessMail() {
        System.out.println("======================Staring filter business mail");
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean("taskExecutor");
//        ExecutorService executor = Executors.newFixedThreadPool(1);
        for (int i = 0; i < 10; i++) {
            Pageable pageable = PageRequest.of(i, SIZE);
            Page<BusinessEmailModel> businessEmailModels = businessEmailRepo.findAll(pageable);
            BusinessEmailService businessEmailService = (BusinessEmailService) applicationContext.getBean("businessEmailService");
//            businessEmailService.setPage(i);
//            businessEmailService.setSize(SIZE);
            businessEmailService.setBusinessEmailModels(businessEmailModels.getContent());
//            businessEmailService.setType("BUSINESS"); // bullshit type
//            try {
                taskExecutor.submit(businessEmailService);
//                Thread.sleep(1300);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }

    @GetMapping("/gmail")
    public void filterGmail() {
        System.out.println("Staring filter gmail");
        for (int i = 0; i < FilterConstraints.GMAILS.length; i++) {
            EmailService businessEmailService = (EmailService) applicationContext.getBean("emailService");
            businessEmailService.setSize(SIZE);
            businessEmailService.setType(FilterConstraints.GMAILS[i]);
            Thread thread = new Thread(businessEmailService);
            thread.start();
        }
    }

    @GetMapping("/gov-mail")
    public void filterGovMail() {
        System.out.println("Staring filter gov mail");
        for (int i = 0; i < FilterConstraints.GOVS.length; i++) {
            GovMailService businessEmailService = (GovMailService) applicationContext.getBean("govMailService");
            businessEmailService.setSize(SIZE);
            businessEmailService.setType(FilterConstraints.GOVS[i]);
            Thread thread = new Thread(businessEmailService);
            thread.start();
        }
    }

    @GetMapping("/invalid-mail")
    public void filterInvalidMail() {
        System.out.println("Staring filter invalid mail");
        for (int i = 0; i < FilterConstraints.INVALIDS.length; i++) {
            InvalidMailService businessEmailService = (InvalidMailService) applicationContext.getBean("invalidMailService");
            businessEmailService.setSize(SIZE);
            businessEmailService.setType(FilterConstraints.INVALIDS[i]);
            Thread thread = new Thread(businessEmailService);
            thread.start();
        }
    }

    @GetMapping("/lsp-mail")
    public void filterLspMail() {
        System.out.println("Staring filter lsp mail");
        for (int i = 0; i < FilterConstraints.LSPS.length; i++) {
            LspMailService businessEmailService = (LspMailService) applicationContext.getBean("lspMailService");
            businessEmailService.setSize(1000);
            businessEmailService.setType(FilterConstraints.LSPS[i]);
            Thread thread = new Thread(businessEmailService);
            thread.start();
        }
    }

    @GetMapping("/others-mail")
    public void filterOthersMail() throws InterruptedException {
        System.out.println("Staring filter others mail");
        for (int i = 0; i < FilterConstraints.OTHERS.length; i++) {
            OthersMailService businessEmailService = (OthersMailService) applicationContext.getBean("othersMailService");
            businessEmailService.setSize(SIZE);
            businessEmailService.setType(FilterConstraints.OTHERS[i]);
            Thread thread = new Thread(businessEmailService);
            thread.start();
            Thread.sleep(1000);
        }
    }

    @GetMapping("/role-mail")
    public void filterRoleMail() {
        System.out.println("Staring filter role mail");
        for (int i = 0; i < FilterConstraints.ROLES.length; i++) {
            RoleMailService businessEmailService = (RoleMailService) applicationContext.getBean("roleMailService");
            businessEmailService.setSize(SIZE);
            businessEmailService.setType(FilterConstraints.ROLES[i]);
            Thread thread = new Thread(businessEmailService);
            thread.start();
        }
    }

    @GetMapping("/vietnam-mail")
    public void filterVietnamMail() {
        System.out.println("Staring filter vietnam mail");
        for (int i = 0; i < FilterConstraints.VIETNAMS.length; i++) {
            VietnamMailService businessEmailService = (VietnamMailService) applicationContext.getBean("vietnamMailService");
            businessEmailService.setSize(SIZE);
            businessEmailService.setType(FilterConstraints.VIETNAMS[i]);
            Thread thread = new Thread(businessEmailService);
            thread.start();
        }
    }

    @GetMapping("/yahoo-mail")
    public void filterYahooMail() {
        System.out.println("Staring filter yahoo mail");
        for (int i = 0; i < FilterConstraints.YAHOOS.length; i++) {
            YahooMailService businessEmailService = (YahooMailService) applicationContext.getBean("yahooMailService");
            businessEmailService.setSize(1000);
            businessEmailService.setType(FilterConstraints.YAHOOS[i]);
            Thread thread = new Thread(businessEmailService);
            thread.start();
        }
    }

    @GetMapping("/autoFilter")
    public void autoDecisionFilter() throws InterruptedException {
        Pageable pageable = PageRequest.of(0, 5);
        Page<IdEmail> idEmailPage = emailRepo.findAll(pageable);
        String mailToFilter = null;
        for (IdEmail idEmail : idEmailPage.getContent()) {
            if (Objects.nonNull(idEmail.getMemberPrimaryEmail())) {
                mailToFilter = idEmail.getMemberPrimaryEmail();
                break;
            }
        }
        System.out.println("Mail Found = " + mailToFilter);
        if (mailToFilter != null) {
            if (AppUtil.containsList(Arrays.asList(FilterConstraints.APPLES), mailToFilter)) {
                filterAppleMail();
            } else if (AppUtil.containsList(Arrays.asList(FilterConstraints.GMAILS), mailToFilter)) {
                filterGmail();
            } else if (AppUtil.containsList(Arrays.asList(FilterConstraints.GOVS), mailToFilter)) {
                filterGovMail();
            } else if (AppUtil.containsList(Arrays.asList(FilterConstraints.INVALIDS), mailToFilter)) {
                filterInvalidMail();
            } else if (AppUtil.containsList(Arrays.asList(FilterConstraints.LSPS), mailToFilter)) {
                filterLspMail();
            } else if (AppUtil.containsList(Arrays.asList(FilterConstraints.OTHERS), mailToFilter)) {
                filterOthersMail();
            } else if (AppUtil.containsList(Arrays.asList(FilterConstraints.ROLES), mailToFilter)) {
                filterRoleMail();
            } else if (AppUtil.containsList(Arrays.asList(FilterConstraints.VIETNAMS), mailToFilter)) {
                filterVietnamMail();
            } else if (AppUtil.containsList(Arrays.asList(FilterConstraints.YAHOOS), mailToFilter)) {
                filterYahooMail();
            } else {
                filterBusinessMail();
            }
        }
    }

    @GetMapping("/auto-run")
    public void autoRun() {
        System.out.println("result = " + AppUtil.hasThreadExecutor());
        System.out.println("result = " + AppUtil.hasThreadBusiness());
    }

    @GetMapping("/downloadBusiness")
    public ResponseEntity<InputStreamResource> downloadExcel(@RequestParam(defaultValue = "20000", required = false) Integer size,
                                                             @RequestParam(defaultValue = "0", required = false) Integer page) {
        ByteArrayInputStream byteArrayInputStream;
        try {
            Workbook workbook = businessEmailService.download(page, size);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            workbook.close();
            byteArrayInputStream = new ByteArrayInputStream(out.toByteArray());
            return ResponseEntity.status(HttpStatus.OK).body(new InputStreamResource(byteArrayInputStream));
        } catch (Exception e) {
            e.getMessage();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
