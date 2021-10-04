package com.saltlux.tool.filter.tool;

import com.saltlux.tool.filter.tool.controller.IndexController;
import com.saltlux.tool.filter.tool.service.EmailService;
import com.saltlux.tool.filter.tool.service.FilterJSONService;
import com.saltlux.tool.filter.tool.util.FilterUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class FilterJSONTest {

    @Autowired
    private FilterJSONService filterJSONService;

    @Autowired
    private IndexController indexController;

    @Autowired
    private EmailService emailService;

    @Test
    public void testFilter() throws IOException {
//        indexController.testFilter();
        emailService.saveToBusiness();
    }
}
