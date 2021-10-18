package com.saltlux.tool.filter.tool.controller;

import com.saltlux.tool.filter.tool.model.IdEmail;
import com.saltlux.tool.filter.tool.repo.EmailRepo;
import com.saltlux.tool.filter.tool.service.FilterAllMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/all")
public class FilterAllMailController {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private EmailRepo emailRepo;

    @Autowired
    private FilterAllMailService filterAllMailService;

    private final Integer NUM_OF_THREAD = 10;

    @GetMapping("/filter")
    public void filterAllMail(@RequestParam(value = "size", defaultValue = "10000", required = false) Integer size) throws InterruptedException {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean("taskExecutor");
//        ExecutorService executorService = Executors.newFixedThreadPool(NUM_OF_THREAD);
        for (int i = 0; i < NUM_OF_THREAD; i++) {
            Pageable pageable = PageRequest.of(i, size);
            Page<IdEmail> idEmailPage = emailRepo.findAll(pageable);
            FilterAllMailService filterAllMailService = applicationContext.getBean(FilterAllMailService.class);
            filterAllMailService.setIdEmails(idEmailPage.getContent());
            taskExecutor.execute(filterAllMailService);
//            Thread.sleep(1000);
        }
//        filterAllMailService.deleteAfterFilter();
    }

    @GetMapping("/delete")
    public void deleteAllMail() throws InterruptedException {
//        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean("taskExecutor");
//        ExecutorService executorService = Executors.newFixedThreadPool(NUM_OF_THREAD);
//        for (int i = 0; i < NUM_OF_THREAD; i++) {
//            Pageable pageable = PageRequest.of(i, size);
//            Page<IdEmail> idEmailPage = emailRepo.findAll(pageable);
//            FilterAllMailService filterAllMailService = applicationContext.getBean(FilterAllMailService.class);
//            filterAllMailService.setIdEmails(idEmailPage.getContent());
//            executorService.execute(filterAllMailService);
//            Thread.sleep(1000);
//        }
        filterAllMailService.deleteAfterFilter();
    }
}
