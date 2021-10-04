package com.saltlux.tool.filter.tool.service;

import com.saltlux.tool.filter.tool.dto.LinkedInAccountDto;
import com.saltlux.tool.filter.tool.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Objects;

@Component
public class FilterJSONService {

    @Autowired
    private ApplicationContext applicationContext;

    private final String SOURCE = "/Users/Saltlux/Downloads/Linkedin_Data";

    public void filterJSON(String name) {
        try {
            final File folder = new File(SOURCE);
//            List<String> names = FileUtil.listFilesForFolder(folder);
            ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) applicationContext.getBean("taskExecutor");
//            name = "sample1-1.json";
//            for (String name : names) {
                if (Objects.nonNull(name) && name.contains(".json")) {
                    LinkedinAccountService linkedinAccountService = applicationContext.getBean(LinkedinAccountService.class);
                    List<LinkedInAccountDto> linkedInAccountDtos = FileUtil.readFileJson(SOURCE + "/" + name);
                    linkedinAccountService.setLinkedInAccountDtos(linkedInAccountDtos);
                    linkedinAccountService.setName(name);
//                    linkedinAccountService.run();
                    taskExecutor.execute(linkedinAccountService);
                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in " + Thread.currentThread().getName());
        }
    }
}
