package com.saltlux.tool.filter.tool.controller;

import com.saltlux.tool.filter.tool.service.BusinessEmailService;
import com.saltlux.tool.filter.tool.service.CustomService;
import com.saltlux.tool.filter.tool.util.FilterConstraints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/custom")
public class CustomController {

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("/filterRole")
    public void filterAgainRole() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean("taskExecutor");
        for (int i = 0; i < FilterConstraints.ROLES.length; i++) {
            CustomService businessEmailService = (CustomService) applicationContext.getBean("customService");
            for (int j = 0; j < 20; j++) {
                businessEmailService.setPage(0);
                businessEmailService.setSize(3000);
                businessEmailService.setType(FilterConstraints.ROLES[i]); // bullshit type
                try {
                    taskExecutor.submit(businessEmailService);
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @GetMapping("/filterBusiness")
    public void filterBusinessToOther() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean("taskExecutor");
        for (int i = 0; i < FilterConstraints.OTHERS.length; i++) {
            BusinessEmailService businessEmailService = (BusinessEmailService) applicationContext.getBean("businessEmailService");
//            for (int j = 0; j < 20; j++) {
                businessEmailService.setPage(0);
                businessEmailService.setSize(3000);
                businessEmailService.setType(FilterConstraints.OTHERS[i]); // bullshit type
                try {
                    taskExecutor.submit(businessEmailService);
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//            }
        }
    }
}
