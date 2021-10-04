package com.saltlux.tool.filter.tool.controller;

import com.saltlux.tool.filter.tool.dto.StatisticBusinessDto;
import com.saltlux.tool.filter.tool.dto.StatisticDto;
import com.saltlux.tool.filter.tool.repo.*;
import com.saltlux.tool.filter.tool.service.FilterJSONService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/index")
public class IndexController {

    private final AppleRepo appleRepo;
    private final BusinessEmailRepo businessEmailRepo;
    private final GmailRepo gmailRepo;
    private final GovRepo govRepo;
    private final InvalidRepo invalidRepo;
    private final LspRepo lspRepo;
    private final OtherRepo otherRepo;
    private final RoleRepo roleRepo;
    private final VietnamRepo vietnamRepo;
    private final YahooEmailRepo yahooEmailRepo;
    private final EmailRepo emailRepo;

    private final BusinessAutomotiveMailRepo businessAutomotiveMailRepo;
    private final BusinessBankingMailRepo businessBankingMailRepo;
    private final BusinessFashionMailRepo businessFashionMailRepo;
    private final BusinessGlobalMailRepo businessGlobalMailRepo;
    private final BusinessHealthCareMailRepo businessHealthCareMailRepo;
    private final BusinessHotelMailRepo businessHotelMailRepo;
    private final BusinessLawMailRepo businessLawMailRepo;
    private final BusinessMediaMailRepo businessMediaMailRepo;
    private final BusinessTechMailRepo businessTechMailRepo;

    @Autowired
    public IndexController(AppleRepo appleRepo, BusinessEmailRepo businessEmailRepo, GmailRepo gmailRepo,
                           GovRepo govRepo, InvalidRepo invalidRepo, LspRepo lspRepo, OtherRepo otherRepo,
                           RoleRepo roleRepo, VietnamRepo vietnamRepo, YahooEmailRepo yahooEmailRepo, EmailRepo emailRepo,
                           BusinessAutomotiveMailRepo businessAutomotiveMailRepo, BusinessBankingMailRepo businessBankingMailRepo,
                           BusinessFashionMailRepo businessFashionMailRepo, BusinessGlobalMailRepo businessGlobalMailRepo,
                           BusinessHealthCareMailRepo businessHealthCareMailRepo, BusinessHotelMailRepo businessHotelMailRepo,
                           BusinessLawMailRepo businessLawMailRepo, BusinessMediaMailRepo businessMediaMailRepo, BusinessTechMailRepo businessTechMailRepo
                           ) {
        this.appleRepo = appleRepo;
        this.businessEmailRepo = businessEmailRepo;
        this.gmailRepo = gmailRepo;
        this.govRepo = govRepo;
        this.invalidRepo = invalidRepo;
        this.lspRepo = lspRepo;
        this.otherRepo = otherRepo;
        this.roleRepo = roleRepo;
        this.vietnamRepo = vietnamRepo;
        this.yahooEmailRepo = yahooEmailRepo;
        this.emailRepo = emailRepo;
        this.businessAutomotiveMailRepo = businessAutomotiveMailRepo;
        this.businessBankingMailRepo = businessBankingMailRepo;
        this.businessFashionMailRepo = businessFashionMailRepo;
        this.businessGlobalMailRepo = businessGlobalMailRepo;
        this.businessHealthCareMailRepo = businessHealthCareMailRepo;
        this.businessHotelMailRepo = businessHotelMailRepo;
        this.businessLawMailRepo = businessLawMailRepo;
        this.businessMediaMailRepo = businessMediaMailRepo;
        this.businessTechMailRepo = businessTechMailRepo;
    }

    @Autowired
    private FilterJSONService filterJSONService;

    @RequestMapping("/statistic")
    public ResponseEntity<?> index() {
//        StatisticDto statisticDto = new StatisticDto();
        return ResponseEntity.status(HttpStatus.OK).body(
                new StatisticDto(appleRepo.count(), gmailRepo.count(), businessEmailRepo.count(),
                        govRepo.count(), invalidRepo.count(), lspRepo.count(), otherRepo.count(),
                        roleRepo.count(), vietnamRepo.count(), yahooEmailRepo.count(), emailRepo.count()));
    }

    @RequestMapping("/statistic-business")
    public ResponseEntity<?> statisticBusiness() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new StatisticBusinessDto(businessAutomotiveMailRepo.count(), businessBankingMailRepo.count(),
                        businessFashionMailRepo.count(), businessGlobalMailRepo.count(), businessHealthCareMailRepo.count(),
                        businessHotelMailRepo.count(), businessLawMailRepo.count(), businessMediaMailRepo.count(),
                        businessTechMailRepo.count(), businessEmailRepo.count()));
    }

    @GetMapping("/filter")
    public void testFilter(@RequestParam String name) throws IOException {
        filterJSONService.filterJSON(name);
    }
}
