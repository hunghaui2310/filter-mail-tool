package com.saltlux.tool.filter.tool.controller;

import com.saltlux.tool.filter.tool.model.BusinessHotelModel;
import com.saltlux.tool.filter.tool.model.IdEmail;
import com.saltlux.tool.filter.tool.repo.BusinessHotelMailRepo;
import com.saltlux.tool.filter.tool.service.BusinessEmailService;
import com.saltlux.tool.filter.tool.service.FilterAllMailService;
import com.saltlux.tool.filter.tool.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/filter")
public class FilterAgainController {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private BusinessHotelMailRepo businessHotelMailRepo;

    @GetMapping("/again")
    public void filterAgainHotel() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean("taskExecutor");
        for (int i = 0; i < 10; i++) {
            Pageable pageable = PageRequest.of(i, 1000);
            Page<BusinessHotelModel> idEmailPage = businessHotelMailRepo.findAll(pageable);
            HotelService filterAllMailService = applicationContext.getBean(HotelService.class);
            filterAllMailService.setBusinessHotelModels(idEmailPage.getContent());
            taskExecutor.execute(filterAllMailService);
        }
    }
}
