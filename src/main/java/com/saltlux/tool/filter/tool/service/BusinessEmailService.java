package com.saltlux.tool.filter.tool.service;

import com.saltlux.tool.filter.tool.model.*;
import com.saltlux.tool.filter.tool.repo.*;
import com.saltlux.tool.filter.tool.util.AppUtil;
import com.saltlux.tool.filter.tool.util.FilterConstraints;
import com.saltlux.tool.filter.tool.util.FilterUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@Getter
@Setter
public class BusinessEmailService implements Runnable {

    private Integer page;
    private Integer size;
    private String type;

    @Autowired
    private BusinessEmailRepo businessEmailRepo;

    @Autowired
    private EmailRepo emailRepo;

    @Autowired
    private OtherRepo otherRepo;

    public BusinessEmailService(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    @Transactional(dontRollbackOn = Exception.class)
    public void filterBusinessToOther() {
        Pageable pageable = PageRequest.of(page, size);
        Page<BusinessEmailModel> idEmails = businessEmailRepo.findAllByMemberPrimaryEmailIgnoreCaseContaining(pageable, type);
        List<OtherModel> businessEmailModels = AppUtil.mapAll(idEmails.getContent(), OtherModel.class);
        List<String> ids = businessEmailModels.stream().map(OtherModel::getMemberId).collect(Collectors.toList());
        otherRepo.saveAll(businessEmailModels);
        businessEmailRepo.deleteAllByIdInBatch(ids);
        System.out.println("Business mail with concurrent task executor ============== DONE " + Thread.currentThread().getName());
    }

    @Transactional(dontRollbackOn = Exception.class)
    public void filterBusinessMail() throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        Page<IdEmail> idEmails = emailRepo.findAll(pageable);
//        System.out.println("Business mail with page = " + page + " and type = " + type + " = " + idEmails.getTotalElements() + ". Thread: " + Thread.currentThread().getName());
//        List<BusinessEmailModel> businessEmailModels = AppUtil.mapAll(idEmails.getContent(), BusinessEmailModel.class)
//                .stream()
//                .filter(item -> Objects.nonNull(item.getMemberPrimaryEmail()) && FilterUtil.filterBusinessEmail(item.getMemberPrimaryEmail()))
//                .collect(Collectors.toList());
//        List<String> ids = businessEmailModels.stream().map(BusinessEmailModel::getMemberId).collect(Collectors.toList());
        LinkedList<BusinessEmailModel> businessEmailModels = new LinkedList<>();
        LinkedList<String> ids = new LinkedList<>();
        for (IdEmail idEmail : idEmails) {
            String email = idEmail.getMemberPrimaryEmail();
            String memberId = idEmail.getMemberId();
            if (Objects.nonNull(email)) {
                if (FilterUtil.filterBusinessEmail(email)) {
                    businessEmailModels.add(new BusinessEmailModel(memberId, email));
                    ids.add(memberId);
                }
            } else {
                ids.add(memberId);
            }
        }
        businessEmailRepo.saveAll(businessEmailModels);
        emailRepo.deleteAllByIdInBatch(ids);
//        System.out.println("Business mail ============== DONE " + businessEmailRepo.count());
        Thread.currentThread().setDaemon(false);
    }

    @Transactional(dontRollbackOn = Exception.class)
    public void filterBusinessWithParam(int page, int size) throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        Page<IdEmail> idEmails = emailRepo.findAll(pageable);
        List<BusinessEmailModel> businessEmailModels = AppUtil.mapAll(idEmails.getContent(), BusinessEmailModel.class)
                .stream()
                .filter(item -> Objects.nonNull(item.getMemberPrimaryEmail()) && FilterUtil.filterBusinessEmail(item.getMemberPrimaryEmail()))
                .collect(Collectors.toList());
        List<String> ids = businessEmailModels.stream().map(BusinessEmailModel::getMemberId).collect(Collectors.toList());
        businessEmailRepo.saveAll(businessEmailModels);
        emailRepo.deleteAllByIdInBatch(ids);
        System.out.println("Business mail ============== DONE ");
//        Thread.currentThread().setDaemon(false);
    }

    public Workbook download(int start, int end) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        Pageable pageable = PageRequest.of(start, end);
        Page<BusinessEmailModel> idEmails = businessEmailRepo.findAll(pageable);
        for (int i = 0; i < idEmails.getTotalPages(); i++) {
            Row dataRow = sheet.createRow(i);
            Cell dataCell = dataRow.createCell(0);
            dataCell.setCellValue(idEmails.getContent().get(i).getMemberPrimaryEmail());
        }
        return workbook;
    }

    @Override
    public void run() {
        try {
            filterBusinessMail();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public long countAll() {
        return businessEmailRepo.count();
    }
}
