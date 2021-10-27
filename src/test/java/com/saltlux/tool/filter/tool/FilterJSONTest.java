package com.saltlux.tool.filter.tool;

import com.saltlux.tool.filter.tool.controller.FilterAgainController;
import com.saltlux.tool.filter.tool.controller.IndexController;
import com.saltlux.tool.filter.tool.model.AppleModel;
import com.saltlux.tool.filter.tool.model.BusinessGlobalModel;
import com.saltlux.tool.filter.tool.repo.AppleRepo;
import com.saltlux.tool.filter.tool.repo.BusinessGlobalMailRepo;
import com.saltlux.tool.filter.tool.service.EmailService;
import com.saltlux.tool.filter.tool.service.FilterJSONService;
import com.saltlux.tool.filter.tool.service.FinalService;
import com.saltlux.tool.filter.tool.util.FilterUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.List;

@SpringBootTest
public class FilterJSONTest {

    @Autowired
    private FilterJSONService filterJSONService;

    @Autowired
    private IndexController indexController;

    @Autowired
    private FilterAgainController filterAgainController;

    @Autowired
    private FinalService finalService;

    @Autowired
    private AppleRepo appleRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BusinessGlobalMailRepo businessGlobalMailRepo;

    @Test
    public void testFilter() throws IOException {
//        indexController.testFilter();
        emailService.saveToBusiness();
    }

    @Test
    public void filterAgain() {
        filterAgainController.filterAgainHotel();
    }

    @Test
    public void saveAgainTest() {
        Page<BusinessGlobalModel> appleModels = businessGlobalMailRepo.findAll(PageRequest.of(0, 10));
        finalService.filterAgain(appleModels.getContent());
    }
}
