package com.saltlux.tool.filter.tool.controller;

import com.saltlux.tool.filter.tool.model.StatisticDto;
import com.saltlux.tool.filter.tool.repo.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public IndexController(AppleRepo appleRepo, BusinessEmailRepo businessEmailRepo, GmailRepo gmailRepo,
                           GovRepo govRepo, InvalidRepo invalidRepo, LspRepo lspRepo, OtherRepo otherRepo,
                           RoleRepo roleRepo, VietnamRepo vietnamRepo, YahooEmailRepo yahooEmailRepo) {
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
    }

    @RequestMapping("/statistic")
    public ResponseEntity<?> index() {
//        StatisticDto statisticDto = new StatisticDto();
        return ResponseEntity.status(HttpStatus.OK).body(
                new StatisticDto(appleRepo.count(), gmailRepo.count(), businessEmailRepo.count(),
                        govRepo.count(), invalidRepo.count(), lspRepo.count(), otherRepo.count(),
                        roleRepo.count(), vietnamRepo.count(), yahooEmailRepo.count()));
    }
}
