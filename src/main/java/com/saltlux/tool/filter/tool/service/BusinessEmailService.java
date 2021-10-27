package com.saltlux.tool.filter.tool.service;

import com.saltlux.tool.filter.tool.model.*;
import com.saltlux.tool.filter.tool.repo.*;
import com.saltlux.tool.filter.tool.util.AppUtil;
import com.saltlux.tool.filter.tool.util.BusinessMailType;
import com.saltlux.tool.filter.tool.util.FilterUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
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
    private List<? extends BaseModel> baseModels = new ArrayList<>();
    private List<BaseModel> baseModelsToDelete;
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
//        LinkedList<BusinessEmailModel> businessEmailModels = new LinkedList<>();
//        LinkedList<String> ids = new LinkedList<>();
//        for (IdEmail idEmail : idEmails) {
//            String email = idEmail.getMemberPrimaryEmail();
//            String memberId = idEmail.getMemberId();
//            if (Objects.nonNull(email)) {
//                if (FilterUtil.filterBusinessEmail(email)) {
//                    businessEmailModels.add(new BusinessEmailModel(memberId, email));
//                    ids.add(memberId);
//                }
//            } else {
//                ids.add(memberId);
//            }
//        }
//        businessEmailRepo.saveAll(businessEmailModels);
//        emailRepo.deleteAllByIdInBatch(ids);
//        System.out.println("Business mail ============== DONE ");
//        Thread.currentThread().setDaemon(false);
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
        filterFromBusiness(this.baseModels);
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
    public List<? extends BaseModel> filterFromBusiness(List<? extends BaseModel> businessEmailModels) {
        if (businessEmailModels.size() == 0) {
            return null;
        }
        this.baseModelsToDelete = new ArrayList<>();
        List<? extends BaseModel> baseModelsOut = null;
        if (!(businessEmailModels.get(0) instanceof BusinessLawModel)) {
            baseModelsOut = filterBusinessByType(businessEmailModels, BusinessMailType.LAW);
        }
        if (!(businessEmailModels.get(0) instanceof BusinessBankingModel)) {
            baseModelsOut = filterBusinessByType(baseModelsOut, BusinessMailType.BANKING);
        }
        if (!(businessEmailModels.get(0) instanceof BusinessTechModel)) {
            baseModelsOut = filterBusinessByType(baseModelsOut, BusinessMailType.TECH);
        }
        if (!(businessEmailModels.get(0) instanceof BusinessAutomotiveModel)) {
            baseModelsOut = filterBusinessByType(baseModelsOut, BusinessMailType.AUTOMOTIVE);
        }
        if (!(businessEmailModels.get(0) instanceof BusinessHealthCareModel)) {
            baseModelsOut = filterBusinessByType(baseModelsOut, BusinessMailType.HEALTH_CARE);
        }
        if (!(businessEmailModels.get(0) instanceof BusinessMediaModel)) {
            baseModelsOut = filterBusinessByType(baseModelsOut, BusinessMailType.MEDIA);
        }
        if (!(businessEmailModels.get(0) instanceof BusinessGlobalModel)) {
            baseModelsOut = filterBusinessByType(baseModelsOut, BusinessMailType.GLOBAL);
        }
        if (!(businessEmailModels.get(0) instanceof BusinessHotelModel)) {
            baseModelsOut = filterBusinessByType(baseModelsOut, BusinessMailType.HOTEL);
        }
        if (!(businessEmailModels.get(0) instanceof BusinessFashionModel)) {
            baseModelsOut = filterBusinessByType(baseModelsOut, BusinessMailType.FASHION);
        }
//        if (Objects.nonNull(listAfterFilterFashion) && listAfterFilterFashion.size() > 0) {
//            businessEmailModels.removeAll(listAfterFilterFashion);
//            Map<Boolean, List<BusinessEmailModel>> numbersByIsPositive = listAfterFilterFashion.stream()
//                    .collect(Collectors.groupingBy(mail -> (Objects.nonNull(mail.getMemberPrimaryEmail()) && FilterUtil.filterLSPMail(mail.getMemberPrimaryEmail()))));
//            List<BusinessEmailModel> businessEmailModelsLsp = numbersByIsPositive.get(true);
//            if (Objects.nonNull(businessEmailModelsLsp) && businessEmailModelsLsp.size() > 0) {
//                List<LspModel> lspModels = AppUtil.mapAll(businessEmailModelsLsp, LspModel.class);
//                lspRepo.saveAll(lspModels);
//            }
//            List<BusinessEmailModel> businessEmailModelRest = numbersByIsPositive.get(false);
//            if (Objects.nonNull(businessEmailModelRest) && businessEmailModelRest.size() > 0) {
//                List<IdEmail> idEmailList = AppUtil.mapAll(businessEmailModelRest, IdEmail.class);
//                emailRepo.saveAll(idEmailList);
//            }
//        }
//        System.out.println("Total mail to delete: " + this.businessEmailModelsRest.size() + " in thread: " + Thread.currentThread().getName());
//        businessEmailRepo.deleteAllByIdInBatch(businessEmailModels.stream().map(BusinessEmailModel::getMemberId).collect(Collectors.toList()));
        deleteByType(businessEmailModels);
        System.out.println("Done filter in Thread: " + Thread.currentThread().getName());
        return baseModelsOut;
    }

    @Transactional(dontRollbackOn = Exception.class)
    public List<? extends BaseModel> filterFromBusinessAgain(List<? extends BaseModel> businessEmailModels) {
        if (businessEmailModels.size() == 0) {
            return null;
        }
        List<? extends BaseModel> lis1 = filterBusinessByType(businessEmailModels, BusinessMailType.LAW);
        List<? extends BaseModel> lis2 = filterBusinessByType(lis1, BusinessMailType.BANKING);
        List<? extends BaseModel> lis3 = filterBusinessByType(lis2, BusinessMailType.TECH);
        List<? extends BaseModel> lis4 = filterBusinessByType(lis3, BusinessMailType.AUTOMOTIVE);
        List<? extends BaseModel> lis5 = filterBusinessByType(lis4, BusinessMailType.HEALTH_CARE);
        List<? extends BaseModel> lis6 = filterBusinessByType(lis5, BusinessMailType.MEDIA);
        List<? extends BaseModel> lis7 = filterBusinessByType(lis6, BusinessMailType.GLOBAL);
        List<? extends BaseModel> lis8 = filterBusinessByType(lis7, BusinessMailType.HOTEL);
        List<? extends BaseModel> lis9 = filterBusinessByType(lis8, BusinessMailType.FASHION);
//        deleteByType(businessEmailModels);
        return lis9;
    }

    @Transactional(dontRollbackOn = Exception.class)
    public List<? extends BaseModel> filterBusinessByType(List<? extends BaseModel> businessEmailModels, BusinessMailType businessMailType) {
        if (Objects.isNull(businessEmailModels) || businessEmailModels.size() == 0) {
            return null;
        }
        Map<Boolean, List<BaseModel>> numbersByIsPositive = businessEmailModels.stream()
                .collect(Collectors.groupingBy(mail -> (Objects.nonNull(mail.getMemberPrimaryEmail()) && FilterUtil.filterBusinessByType(mail.getMemberPrimaryEmail(), businessMailType))));
        List<BaseModel> idEmailsOut = numbersByIsPositive.get(true);
        if (Objects.nonNull(idEmailsOut) && idEmailsOut.size() > 0) {
//            this.baseModelsToDelete.addAll(idEmailsOut);
            switch (businessMailType) {
                case GLOBAL:
                    List<BusinessGlobalModel> businessGlobalModels = AppUtil.mapAll(idEmailsOut, BusinessGlobalModel.class);
                    businessGlobalMailRepo.saveAll(businessGlobalModels);
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

    @Transactional(dontRollbackOn = Exception.class)
    public void deleteByType(List<? extends BaseModel> baseModels) {
        List<String> ids = baseModelsToDelete.stream().map(BaseModel::getMemberId).collect(Collectors.toList());
        if (ids.size() > 0) {
            if (baseModels.get(0) instanceof BusinessAutomotiveModel) {
                businessAutomotiveMailRepo.deleteAllByIdInBatch(ids);
            } else if (baseModels.get(0) instanceof BusinessBankingModel) {
                businessBankingMailRepo.deleteAllByIdInBatch(ids);
            } else if (baseModels.get(0) instanceof BusinessFashionModel) {
                businessFashionMailRepo.deleteAllByIdInBatch(ids);
            } else if (baseModels.get(0) instanceof BusinessGlobalModel) {
                businessGlobalMailRepo.deleteAllByIdInBatch(ids);
            } else if (baseModels.get(0) instanceof BusinessHealthCareModel) {
                businessHealthCareMailRepo.deleteAllByIdInBatch(ids);
            } else if (baseModels.get(0) instanceof BusinessHotelModel) {
                businessHotelMailRepo.deleteAllByIdInBatch(ids);
            } else if (baseModels.get(0) instanceof BusinessLawModel) {
                businessLawMailRepo.deleteAllByIdInBatch(ids);
            } else if (baseModels.get(0) instanceof BusinessMediaModel) {
                businessMediaMailRepo.deleteAllByIdInBatch(ids);
            } else if (baseModels.get(0) instanceof BusinessTechModel) {
                businessTechMailRepo.deleteAllByIdInBatch(ids);
            }
        }
    }
}
