package com.saltlux.tool.filter.tool.controller;

import com.saltlux.tool.filter.tool.service.BusinessEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/executor")
public class ExecutorController {

    @Autowired
    private BusinessEmailService businessEmailService;

    @GetMapping("/business")
    public void filterBusinessMail() {
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        for (int i = 0; i < 50; i++) {
            int finalI = i;
            executorService.execute(() -> {
                try {
                    businessEmailService.filterBusinessWithParam(finalI, 3000);
                } catch (Exception e) {
                    e.getMessage();
                }
            });
        }
    }
}
