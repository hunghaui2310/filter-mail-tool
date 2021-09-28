package com.saltlux.tool.filter.tool.controller;

import com.saltlux.tool.filter.tool.model.IdEmail;
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
    private ApplicationContext applicationContext;

    private final Integer NUM_OF_THREAD = 5;

    @GetMapping("/apple-mail")
    public void filterAppleMail() {
        System.out.println("Staring filter apple mail");
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean("taskExecutor");
        for (int i = 0; i < FilterConstraints.APPLES.length; i++) {
//            for (int j = 0; j < 5; j++) {
                AppleMailService businessEmailService = (AppleMailService) applicationContext.getBean("appleMailService");
                businessEmailService.setSize(1000);
                businessEmailService.setType(FilterConstraints.APPLES[i]);
//                try {
                    taskExecutor.submit(businessEmailService);
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                int count = taskExecutor.getActiveCount();
//                if (count == 0) {
//                    System.out.println("Killed all Thread In Apple Mail");
//                }
//            }
        }
    }

    @GetMapping("/business")
    public void filterBusinessMail() {
        System.out.println("Staring filter business mail");
//        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean("taskExecutor");
        ExecutorService executor = Executors.newFixedThreadPool(30);
        for (int i = 0; i < 30; i++) {
            BusinessEmailService businessEmailService = (BusinessEmailService) applicationContext.getBean("businessEmailService");
            businessEmailService.setPage(i);
            businessEmailService.setSize(1000);
            businessEmailService.setType("BUSINESS"); // bullshit type
            try {
                executor.submit(businessEmailService);
                Thread.sleep(1300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            int count = taskExecutor.getActiveCount();
//            if (count == 0) {
//                System.out.println("Killed all Thread in Business Mail");
//            }
        }
    }

    @GetMapping("/gmail")
    public void filterGmail() {
        System.out.println("Staring filter gmail");
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean("taskExecutor");
        for (int i = 0; i < FilterConstraints.GMAILS.length; i++) {
//            for (int j = 0; j < 10; j++) {
                EmailService businessEmailService = (EmailService) applicationContext.getBean("emailService");
                businessEmailService.setPage(0);
                businessEmailService.setSize(1000);
                businessEmailService.setType(FilterConstraints.GMAILS[i]);
                try {
                    taskExecutor.submit(businessEmailService);
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int count = taskExecutor.getActiveCount();
                if (count == 0) {
                    System.out.println("Killed all Thread In Gmail");
                }
//            }
        }
    }

    @GetMapping("/gov-mail")
    public void filterGovMail() {
        System.out.println("Staring filter gov mail");
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean("taskExecutor");
        for (int i = 0; i < FilterConstraints.GOVS.length; i++) {
//            for (int j = 0; j < 5; j++) {
                GovMailService businessEmailService = (GovMailService) applicationContext.getBean("govMailService");
                businessEmailService.setPage(0);
                businessEmailService.setSize(1000);
                businessEmailService.setType(FilterConstraints.GOVS[i]);
                try {
                    taskExecutor.submit(businessEmailService);
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int count = taskExecutor.getActiveCount();
                if (count == 0) {
                    System.out.println("Killed all Thread In Gov Mail");
                }
//            }
        }
    }

    @GetMapping("/invalid-mail")
    public void filterInvalidMail() {
        System.out.println("Staring filter invalid mail");
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean("taskExecutor");
        for (int i = 0; i < FilterConstraints.INVALIDS.length; i++) {
//            for (int j = 0; j < 5; j++) {
                InvalidMailService businessEmailService = (InvalidMailService) applicationContext.getBean("invalidMailService");
                businessEmailService.setPage(0);
                businessEmailService.setSize(1000);
                businessEmailService.setType(FilterConstraints.INVALIDS[i]);
                try {
                    taskExecutor.submit(businessEmailService);
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int count = taskExecutor.getActiveCount();
                if (count == 0) {
                    System.out.println("Killed all Thread In Invalid Mail");
                }
//            }
        }
    }

    @GetMapping("/lsp-mail")
    public void filterLspMail() {
        System.out.println("Staring filter lsp mail");
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean("taskExecutor");
        for (int i = 0; i < FilterConstraints.LSPS.length; i++) {
//            for (int j = 0; j < 5; j++) {
                LspMailService businessEmailService = (LspMailService) applicationContext.getBean("lspMailService");
                businessEmailService.setPage(0);
                businessEmailService.setSize(1000);
                businessEmailService.setType(FilterConstraints.LSPS[i]);
                try {
                    taskExecutor.submit(businessEmailService);
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int count = taskExecutor.getActiveCount();
                if (count == 0) {
                    System.out.println("Killed all Thread In Lsp Mail");
                }
//            }
        }
    }

    @GetMapping("/others-mail")
    public void filterOthersMail() {
        System.out.println("Staring filter others mail");
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean("taskExecutor");
//        ExecutorService executor = Executors.newFixedThreadPool(FilterConstraints.OTHERS.length);
        for (int i = 0; i < FilterConstraints.OTHERS.length; i++) {
//            for (int j = 0; j < NUM_OF_THREAD; j++) {
                OthersMailService businessEmailService = (OthersMailService) applicationContext.getBean("othersMailService");
                businessEmailService.setPage(0);
                businessEmailService.setSize(1000);
                businessEmailService.setType(FilterConstraints.OTHERS[i]);
                try {
                    taskExecutor.submit(businessEmailService);
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                int count = taskExecutor.getActiveCount();
//                if (count == 0) {
//                    System.out.println("Killed all Thread In Others Mail");
//                }
//            }
        }
    }

    @GetMapping("/role-mail")
    public void filterRoleMail() {
        System.out.println("Staring filter role mail");
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean("taskExecutor");
        for (int i = 0; i < FilterConstraints.ROLES.length; i++) {
//            for (int j = 0; j < 5; j++) {
                RoleMailService businessEmailService = (RoleMailService) applicationContext.getBean("roleMailService");
                businessEmailService.setPage(0);
                businessEmailService.setSize(1000);
                businessEmailService.setType(FilterConstraints.ROLES[i]);
                try {
                    taskExecutor.submit(businessEmailService);
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int count = taskExecutor.getActiveCount();
                if (count == 0) {
                    System.out.println("Killed all Thread In Role Mail");
                }
//            }
        }
    }

    @GetMapping("/vietnam-mail")
    public void filterVietnamMail() {
        System.out.println("Staring filter vietnam mail");
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean("taskExecutor");
        for (int i = 0; i < FilterConstraints.VIETNAMS.length; i++) {
//            for (int j = 0; j < 5; j++) {
                VietnamMailService businessEmailService = (VietnamMailService) applicationContext.getBean("vietnamMailService");
                businessEmailService.setPage(0);
                businessEmailService.setSize(1000);
                businessEmailService.setType(FilterConstraints.VIETNAMS[i]);
                try {
                    taskExecutor.submit(businessEmailService);
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int count = taskExecutor.getActiveCount();
                if (count == 0) {
                    System.out.println("Killed all Thread In VIETNAM Mail");
                }
//            }
        }
    }

    @GetMapping("/yahoo-mail")
    public void filterYahooMail() {
        System.out.println("Staring filter yahoo mail");
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean("taskExecutor");
        for (int i = 0; i < FilterConstraints.YAHOOS.length; i++) {
//            for (int j = 0; j < 5; j++) {
                YahooMailService businessEmailService = (YahooMailService) applicationContext.getBean("yahooMailService");
                businessEmailService.setPage(0);
                businessEmailService.setSize(1000);
                businessEmailService.setType(FilterConstraints.YAHOOS[i]);
                try {
                    taskExecutor.submit(businessEmailService);
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                int count = taskExecutor.getActiveCount();
//                if (count == 0) {
//                    System.out.println("Killed all Thread In Yahoo Mail");
//                }
//            }
        }
    }

    @GetMapping("/autoFilter")
    public void autoDecisionFilter() {
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
