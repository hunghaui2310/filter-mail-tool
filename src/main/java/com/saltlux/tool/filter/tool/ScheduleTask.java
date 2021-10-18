package com.saltlux.tool.filter.tool;

import com.saltlux.tool.filter.tool.controller.*;
import com.saltlux.tool.filter.tool.service.FilterAllMailService;
import com.saltlux.tool.filter.tool.service.SaveAllService;
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

    @Autowired
    private FilterAllMailController filterAllMailController;

    @Autowired
    private FilterAgainController filterAgainController;

    @Autowired
    private FilterAllMailService filterAllMailService;

    @Autowired
    private IndexController indexController;

    @Scheduled(fixedRate = 15000)
    public void call() throws InterruptedException {
        indexController.filterAgainBusiness();
//        filterAgainController.filterAgainHotel();
//        testController.filterBusinessMail();
//        filterAllMailController.filterAllMail(5000);
//        filterAllMailService.deleteAfterFilter();
    }
}
