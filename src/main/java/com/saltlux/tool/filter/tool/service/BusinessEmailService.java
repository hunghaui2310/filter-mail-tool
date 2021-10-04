package com.saltlux.tool.filter.tool.service;

import com.saltlux.tool.filter.tool.model.*;
import com.saltlux.tool.filter.tool.repo.*;
import com.saltlux.tool.filter.tool.util.AppUtil;
import com.saltlux.tool.filter.tool.util.BusinessMailType;
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
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
@NoArgsConstructor
@Getter
@Setter
public class BusinessEmailService implements Runnable, IFilterService {

    private Integer page;
    private Integer size;
    private String type;
    private List<BusinessEmailModel> businessEmailModels = new LinkedList<>();
//    private List<BusinessEmailModel> businessEmailModelsRest = new LinkedList<>();

    private EmailRepo emailRepo;
    private BusinessEmailRepo businessEmailRepo;
    private BusinessAutomotiveMailRepo businessAutomotiveMailRepo;
    private BusinessBankingMailRepo businessBankingMailRepo;
    private BusinessFashionMailRepo businessFashionMailRepo;
    private BusinessGlobalMailRepo businessGlobalMailRepo;
    private BusinessHealthCareMailRepo businessHealthCareMailRepo;
    private BusinessHotelMailRepo businessHotelMailRepo;
    private BusinessLawMailRepo businessLawMailRepo;
    private BusinessMediaMailRepo businessMediaMailRepo;
    private BusinessTechMailRepo businessTechMailRepo;
    private LspRepo lspRepo;

    @Autowired
    public BusinessEmailService(EmailRepo emailRepo, BusinessEmailRepo businessEmailRepo, BusinessAutomotiveMailRepo businessAutomotiveMailRepo,
                                BusinessBankingMailRepo businessBankingMailRepo, BusinessFashionMailRepo businessFashionMailRepo,
                                BusinessGlobalMailRepo businessGlobalMailRepo, BusinessHealthCareMailRepo businessHealthCareMailRepo,
                                BusinessHotelMailRepo businessHotelMailRepo, BusinessLawMailRepo businessLawMailRepo,
                                BusinessMediaMailRepo businessMediaMailRepo, BusinessTechMailRepo businessTechMailRepo, LspRepo lspRepo) {
        this.emailRepo = emailRepo;
        this.businessEmailRepo = businessEmailRepo;
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
    }

    public BusinessEmailService(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    @Transactional(dontRollbackOn = Exception.class)
    public void filterBusinessToOther() {

    }

    @Transactional(dontRollbackOn = Exception.class)
    public void filterBusinessMail() throws Exception {
        Pageable pageable = PageRequest.of(page, size);
        long start2 = System.currentTimeMillis();
        Page<IdEmail> idEmails = emailRepo.findAll(pageable);
        long end2 = System.currentTimeMillis();
        System.out.println("Business mail with page = " + page + " and type = " + type + ". Thread: " + Thread.currentThread().getName() + ",time = " + (end2- start2));
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
        System.out.println("Business mail ============== DONE ");
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
        filterFromBusiness(this.businessEmailModels);
    }

    public long countAll() {
        return businessEmailRepo.count();
    }

    @Transactional
    public void saveAll(List<BusinessEmailModel> businessEmailModels) {
        if (Objects.isNull(businessEmailModels) || businessEmailModels.size() == 0) {
            return;
        }
        businessEmailRepo.saveAll(businessEmailModels);
        System.out.println("Save Success Business Mail");
    }

    @Override
    public void filter(List<IdEmail> idEmails) {
        System.out.println("Called to Business mail Service " + Thread.currentThread().getName());
    }

    @Transactional(dontRollbackOn = Exception.class)
    public void filterFromBusiness(List<BusinessEmailModel> businessEmailModels) {
        List<BusinessEmailModel> listAfterFilterMedia = filterBusinessByType(businessEmailModels, BusinessMailType.MEDIA);
        List<BusinessEmailModel> listAfterFilterHotel = filterBusinessByType(listAfterFilterMedia, BusinessMailType.HOTEL);
        List<BusinessEmailModel> listAfterFilterFashion = filterBusinessByType(listAfterFilterHotel, BusinessMailType.FASHION);
        List<BusinessEmailModel> listAfterFilterLaw = filterBusinessByType(listAfterFilterFashion, BusinessMailType.LAW);
        List<BusinessEmailModel> listAfterFilterHealthCare = filterBusinessByType(listAfterFilterLaw, BusinessMailType.HEALTH_CARE);
        List<BusinessEmailModel> listAfterFilterGlobal = filterBusinessByType(listAfterFilterHealthCare, BusinessMailType.GLOBAL);
        List<BusinessEmailModel> listAfterFilterAutomotive = filterBusinessByType(listAfterFilterGlobal, BusinessMailType.AUTOMOTIVE);
        List<BusinessEmailModel> listAfterFilterBanking = filterBusinessByType(listAfterFilterAutomotive, BusinessMailType.BANKING);
        List<BusinessEmailModel> listAfterFilterTech = filterBusinessByType(listAfterFilterBanking, BusinessMailType.TECH);
        if (Objects.nonNull(listAfterFilterTech) && listAfterFilterTech.size() > 0) {
            Map<Boolean, List<BusinessEmailModel>> numbersByIsPositive = listAfterFilterTech.stream()
                    .collect(Collectors.groupingBy(mail -> (Objects.nonNull(mail.getMemberPrimaryEmail()) && FilterUtil.filterLSPMail(mail.getMemberPrimaryEmail()))));
            List<BusinessEmailModel> businessEmailModelsLsp = numbersByIsPositive.get(true);
            if (Objects.nonNull(businessEmailModelsLsp) && businessEmailModelsLsp.size() > 0) {
                List<LspModel> lspModels = AppUtil.mapAll(businessEmailModelsLsp, LspModel.class);
                lspRepo.saveAll(lspModels);
            }
            List<BusinessEmailModel> businessEmailModelRest = numbersByIsPositive.get(false);
            if (Objects.nonNull(businessEmailModelRest) && businessEmailModelRest.size() > 0) {
                List<IdEmail> idEmailList = AppUtil.mapAll(businessEmailModelRest, IdEmail.class);
                emailRepo.saveAll(idEmailList);
            }
        }
//        System.out.println("Total mail to delete: " + this.businessEmailModelsRest.size() + " in thread: " + Thread.currentThread().getName());
        businessEmailRepo.deleteAllByIdInBatch(businessEmailModels.stream().map(BusinessEmailModel::getMemberId).collect(Collectors.toList()));
        System.out.println("Done filter in Thread: " + Thread.currentThread().getName());
    }

    @Transactional(dontRollbackOn = Exception.class)
    public List<BusinessEmailModel> filterBusinessByType(List<BusinessEmailModel> businessEmailModels, BusinessMailType businessMailType) {
        if (Objects.isNull(businessEmailModels) || businessEmailModels.size() == 0) {
            return null;
        }
        Map<Boolean, List<BusinessEmailModel>> numbersByIsPositive = businessEmailModels.stream()
                .collect(Collectors.groupingBy(mail -> (Objects.nonNull(mail.getMemberPrimaryEmail()) && FilterUtil.filterBusinessByType(mail.getMemberPrimaryEmail(), businessMailType))));
        List<BusinessEmailModel> idEmailsOut = numbersByIsPositive.get(true);
        if (Objects.nonNull(idEmailsOut) && idEmailsOut.size() > 0) {
            switch (businessMailType) {
                case GLOBAL:
                    List<BusinessGlobalModel> globalModels = AppUtil.mapAll(idEmailsOut, BusinessGlobalModel.class);
                    businessGlobalMailRepo.saveAll(globalModels);
                    break;
                case MEDIA:
                    List<BusinessMediaModel> mediaModels = AppUtil.mapAll(idEmailsOut, BusinessMediaModel.class);
                    businessMediaMailRepo.saveAll(mediaModels);
                    break;
                case AUTOMOTIVE:
                    List<BusinessAutomotiveModel> automotiveModels = AppUtil.mapAll(idEmailsOut, BusinessAutomotiveModel.class);
                    businessAutomotiveMailRepo.saveAll(automotiveModels);
                    break;
                case HOTEL:
                    List<BusinessHotelModel> businessHotelModels = AppUtil.mapAll(idEmailsOut, BusinessHotelModel.class);
                    businessHotelMailRepo.saveAll(businessHotelModels);
                    break;
                case FASHION:
                    List<BusinessFashionModel> fashionModels = AppUtil.mapAll(idEmailsOut, BusinessFashionModel.class);
                    businessFashionMailRepo.saveAll(fashionModels);
                    break;
                case BANKING:
                    List<BusinessBankingModel> businessBankingModels = AppUtil.mapAll(idEmailsOut, BusinessBankingModel.class);
                    businessBankingMailRepo.saveAll(businessBankingModels);
                    break;
                case TECH:
                    List<BusinessTechModel> techModels = AppUtil.mapAll(idEmailsOut, BusinessTechModel.class);
                    businessTechMailRepo.saveAll(techModels);
                    break;
                case LAW:
                    List<BusinessLawModel> lawModels = AppUtil.mapAll(idEmailsOut, BusinessLawModel.class);
                    businessLawMailRepo.saveAll(lawModels);
                    break;
                case HEALTH_CARE:
                    List<BusinessHealthCareModel> healthCareModels = AppUtil.mapAll(idEmailsOut, BusinessHealthCareModel.class);
                    businessHealthCareMailRepo.saveAll(healthCareModels);
                    break;
            }
//            this.businessEmailModelsRest.addAll(idEmailsOut);
        }
        return numbersByIsPositive.get(false);
    }
}
