package com.saltlux.tool.filter.tool;

import com.saltlux.tool.filter.tool.controller.ExecutorController;
import com.saltlux.tool.filter.tool.controller.TestController;
import com.saltlux.tool.filter.tool.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {

    @Autowired
    private TestController testController;

    @Autowired
    private ExecutorController executorController;

//    @Scheduled(fixedRate = 50000)
//    public void scheduleTaskWithAppleMail() {
//        System.out.println("Starting Schedule Apple Mail");
//        try {
//            testController.filterAppleMail();
//        } catch (Exception e) {
//            System.out.println("Da xay ra loi " + e.getMessage());
//        }
//    }

//    @Scheduled(fixedRate = 300000)
//    public void scheduleTaskWithGmail() {
//        System.out.println("======================= Starting Schedule Gmail");
//        try {
//            testController.filterGmail();
//        } catch (Exception e) {
//            System.out.println("Da xay ra loi " + e.getMessage());
//        }
//    }

//    @Scheduled(fixedRate = 600000) // ten minutes
//    public void scheduleTaskWithGovMail() {
//        System.out.println("=================== Starting Schedule Gov Mail");
//        try {
//            testController.filterGovMail();
//        } catch (Exception e) {
//            System.out.println("Da xay ra loi " + e.getMessage());
//        }
//    }

//    @Scheduled(fixedRate = 120000)
//    public void scheduleTaskWithInvalidMail() {
//        System.out.println("Starting Schedule Invalid Mail");
//        try {
//            testController.filterInvalidMail();
//        } catch (Exception e) {
//            System.out.println("Da xay ra loi " + e.getMessage());
//        }
//    }
//
//    @Scheduled(fixedRate = 120000)
//    public void scheduleTaskWithLspMail() {
//        System.out.println("Starting Schedule Lsp Mail");
//        try {
//            testController.filterLspMail();
//        } catch (Exception e) {
//            System.out.println("Da xay ra loi " + e.getMessage());
//        }
//    }

//    @Scheduled(fixedRate = 1800000)
//    public void scheduleTaskWithOthersMail() {
//        System.out.println("======================Starting Schedule Others Mail");
//        try {
//            testController.filterOthersMail();
//        } catch (Exception e) {
//            System.out.println("Da xay ra loi " + e.getMessage());
//        }
//    }

//    @Scheduled(fixedRate = 120000)
//    public void scheduleTaskWithVietnamMail() {
//        System.out.println("Starting Schedule Vietnam Mail");
//        try {
//            testController.filterVietnamMail();
//        } catch (Exception e) {
//            System.out.println("Da xay ra loi " + e.getMessage());
//        }
//    }
//
//    @Scheduled(fixedRate = 30000)
//    public void scheduleTaskWithYahooMail() {
//        System.out.println("==================Starting Schedule Yahoo Mail");
//        try {
//            testController.filterYahooMail();
//        } catch (Exception e) {
//            System.out.println("Da xay ra loi " + e.getMessage());
//        }
//    }

//    @Scheduled(fixedRate = 120000)
//    public void scheduleTaskWithBusinessMail() {
//        if (!AppUtil.hasThreadBusiness()) {
//            System.out.println("==================Starting Schedule Business Mail");
//            try {
//                testController.filterBusinessMail();
//            } catch (Exception e) {
//                System.out.println("Da xay ra loi " + e.getMessage());
//            }
//        }
//    }

//    @Scheduled(fixedRate = 90000)
//    public void scheduleTaskAuto() {
//        if (!AppUtil.hasThreadExecutor()) {
//            System.out.println("==================Starting Schedule Auto Detect Mail");
//            try {
//                testController.autoDecisionFilter();
//            } catch (Exception e) {
//                System.out.println("Da xay ra loi " + e.getMessage());
//            }
//        }
//    }

//    @Scheduled(fixedRate = 600000)
//    public void callBusinessMail() {
//        if (!AppUtil.hasThreadBusiness()) {
//            System.out.println("==================Starting Schedule Business Mail");
//            try {
//                executorController.filterBusinessMail();
//            } catch (Exception e) {
//                System.out.println("Da xay ra loi " + e.getMessage());
//            }
//        }
//    }
}
