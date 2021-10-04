package com.saltlux.tool.filter.tool.service;

import com.saltlux.tool.filter.tool.model.BusinessEmailModel;
import com.saltlux.tool.filter.tool.model.IdEmail;
import com.saltlux.tool.filter.tool.model.RoleModel;
import com.saltlux.tool.filter.tool.repo.BusinessEmailRepo;
import com.saltlux.tool.filter.tool.repo.RoleRepo;
import com.saltlux.tool.filter.tool.repo.YahooEmailRepo;
import com.saltlux.tool.filter.tool.util.AppUtil;
import com.saltlux.tool.filter.tool.util.FilterUtil;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Setter
@Getter
public class CustomService implements Runnable{

    private Integer page;
    private Integer size;
    private String type;

    @Autowired
    private BusinessEmailRepo businessEmailRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void run() {
        try {
            filterBusinessToRole();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Transactional(dontRollbackOn = Exception.class)
    public void filterAgainRole() throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        Page<RoleModel> idEmails = roleRepo.findAllByMemberPrimaryEmailIgnoreCaseContaining(pageable, type);
        List<BusinessEmailModel> businessEmailModels = AppUtil.mapAll(idEmails.getContent(), BusinessEmailModel.class)
                .stream()
                .filter(item -> Objects.nonNull(item.getMemberPrimaryEmail()) && !AppUtil.startWithIgnoreCase(item.getMemberPrimaryEmail(), type))
                .collect(Collectors.toList());
        List<String> ids = businessEmailModels.stream().map(BusinessEmailModel::getMemberId).collect(Collectors.toList());
        businessEmailRepo.saveAll(businessEmailModels);
        roleRepo.deleteAllByIdInBatch(ids);
        System.out.println("Role mail ============== DONE " + roleRepo.count());
    }

    @Transactional(dontRollbackOn = Exception.class)
    public void filterBusinessToRole() throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        Page<BusinessEmailModel> idEmails = businessEmailRepo.findAllByMemberPrimaryEmailStartsWith(pageable, type);
        List<RoleModel> businessEmailModels = AppUtil.mapAll(idEmails.getContent(), RoleModel.class);
        List<String> ids = businessEmailModels.stream().map(RoleModel::getMemberId).collect(Collectors.toList());
        roleRepo.saveAll(businessEmailModels);
        businessEmailRepo.deleteAllByIdInBatch(ids);
        System.out.println("Role mail ============== DONE " + roleRepo.count());
    }

    @Transactional(dontRollbackOn = Exception.class)
    public void filterAll() {

    }
}
