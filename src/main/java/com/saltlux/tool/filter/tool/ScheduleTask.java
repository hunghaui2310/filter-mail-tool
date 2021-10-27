package com.saltlux.tool.filter.tool;

import com.saltlux.tool.filter.tool.controller.*;
import com.saltlux.tool.filter.tool.repo.*;
import com.saltlux.tool.filter.tool.service.FilterAllMailService;
import com.saltlux.tool.filter.tool.service.SaveAllService;
import com.saltlux.tool.filter.tool.util.AppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {

    private final BusinessAutomotiveMailRepo businessAutomotiveMailRepo;
    private final BusinessBankingMailRepo businessBankingMailRepo;
    private final BusinessFashionMailRepo businessFashionMailRepo;
    private final BusinessGlobalMailRepo businessGlobalMailRepo;
    private final BusinessHealthCareMailRepo businessHealthCareMailRepo;
    private final BusinessHotelMailRepo businessHotelMailRepo;
    private final BusinessLawMailRepo businessLawMailRepo;
    private final BusinessMediaMailRepo businessMediaMailRepo;
    private final BusinessTechMailRepo businessTechMailRepo;
    private final LspRepo lspRepo;
    private final OrgRepo orgRepo;

    @Autowired
    private IndexController indexController;

    private Integer START = 0;
    private final Integer SIZE = 3000;
    private final Integer NUM_OF_THREAD = 10;

    @Autowired
    public ScheduleTask(BusinessAutomotiveMailRepo businessAutomotiveMailRepo, BusinessBankingMailRepo businessBankingMailRepo,
                        BusinessFashionMailRepo businessFashionMailRepo, BusinessGlobalMailRepo businessGlobalMailRepo,
                        BusinessHealthCareMailRepo businessHealthCareMailRepo, BusinessHotelMailRepo businessHotelMailRepo,
                        BusinessLawMailRepo businessLawMailRepo, BusinessMediaMailRepo businessMediaMailRepo, BusinessTechMailRepo businessTechMailRepo,
                        LspRepo lspRepo, OrgRepo orgRepo) {
        this.businessAutomotiveMailRepo = businessAutomotiveMailRepo;
        this.businessBankingMailRepo = businessBankingMailRepo;
        this.businessFashionMailRepo = businessFashionMailRepo;
        this.businessGlobalMailRepo = businessGlobalMailRepo;
        this.businessHealthCareMailRepo = businessHealthCareMailRepo;
        this.businessHotelMailRepo = businessHotelMailRepo;
        this.businessLawMailRepo = businessLawMailRepo;
        this.businessMediaMailRepo = businessMediaMailRepo;
        this.businessTechMailRepo = businessTechMailRepo;
        this.lspRepo = lspRepo;
        this.orgRepo = orgRepo;
    }

    @Scheduled(fixedRate = 15000)
    public void call() throws InterruptedException {
//        System.out.println("STARTING SAVE NAME WITH START = " + START);
//        long total = orgRepo.count();
//        long page = total / SIZE;
//        if (total % SIZE > 0) {
//            page = page + 1;
//        }
//        if (START < page) {
            indexController.saveAgain(START, SIZE, NUM_OF_THREAD);
//            START += NUM_OF_THREAD;
//        } else {
//            System.out.println("===============XONG ROI================");
//        }
//        filterAgainController.filterAgainHotel();
//        testController.filterBusinessMail();
//        filterAllMailController.filterAllMail(1000);
//        filterAllMailService.deleteAfterFilter();
    }

//    @Scheduled(fixedRate = 10000)
//    public void saveAgain() throws InterruptedException {
//        indexController.saveAgain();
//    }
}
