package com.saltlux.tool.filter.tool.service;

import com.saltlux.tool.filter.tool.dto.LinkedInAccountDto;
import com.saltlux.tool.filter.tool.model.LinkedinAccountModel;
import com.saltlux.tool.filter.tool.repo.LinkedinAccountRepo;
import com.saltlux.tool.filter.tool.util.FilterUtil;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@Setter
@Getter
public class LinkedinAccountService implements Runnable {

    private List<LinkedInAccountDto> linkedInAccountDtos;
    private String name;

    @Autowired
    private LinkedinAccountRepo linkedinAccountRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void run() {
        filterJSONLevel1(this.linkedInAccountDtos);
    }

    @Transactional
    public void filterJSONLevel1(List<LinkedInAccountDto> linkedInAccountDtos) {
        if (Objects.isNull(linkedInAccountDtos) || linkedInAccountDtos.size() == 0) {
            return;
        }
        int i = 0;
        for (LinkedInAccountDto data : linkedInAccountDtos) {
            if (FilterUtil.filterJSONFile(data)) {
                LinkedinAccountModel linkedinAccountModel = new LinkedinAccountModel();
                try {
                    modelMapper.map(data, linkedinAccountModel);
                    linkedinAccountRepo.save(linkedinAccountModel);
                } catch (Exception dataException) {
                    dataException.getMessage();
                    System.out.println("Error in " + data.getId() + data.getCertifications());
                    if (dataException.getMessage().contains("certifications")) {
                        modelMapper.map(data, linkedinAccountModel);
                        linkedinAccountModel.setCertifications(null);
                        linkedinAccountRepo.save(linkedinAccountModel);
                    }
                }
            }
            System.out.println(i+=1);
        }
        System.out.println("Save success in thread: " + Thread.currentThread().getName() + ". File name = " + name);
    }

    @Transactional(dontRollbackOn = Exception.class)
    public void saveAll(@NotNull List<LinkedinAccountModel> linkedinAccountModels) {
        linkedinAccountRepo.saveAll(linkedinAccountModels);
    }
}
