package com.saltlux.tool.filter.tool.service;

import com.saltlux.tool.filter.tool.model.*;
import com.saltlux.tool.filter.tool.repo.*;
import com.saltlux.tool.filter.tool.util.AppUtil;
import com.saltlux.tool.filter.tool.util.BusinessMailType;
import com.saltlux.tool.filter.tool.util.FilterUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Getter
@Setter
public class FinalService implements Runnable {

    private Integer page;
    private Integer size;
    private List<IdEmail> idEmails = new ArrayList<>();
    private List<BusinessEmailModel> businessEmailModels = new ArrayList<>();
    private List<? extends BaseModel> lstDataToSave = new ArrayList<>();

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

    private final LspRepo lspRepo;
    private final AppleRepo appleRepo;
    private final GmailRepo gmailRepo;
    private final GovRepo govRepo;
    private final InvalidRepo invalidRepo;
    private final OtherRepo otherRepo;
    private final RoleRepo roleRepo;
    private final VietnamRepo vietnamRepo;
    private final YahooEmailRepo yahooEmailRepo;
    private final EduRepo eduRepo;
    private final OrgRepo orgRepo;
    private final SaltluxRepo saltluxRepo;
    private final TamRepo tamRepo;

    @Autowired
    private FilterAllMailService filterAllMailService;

    @Autowired
    BusinessEmailService businessEmailService;

    @Autowired
    public FinalService(BusinessEmailRepo businessEmailRepo, BusinessAutomotiveMailRepo businessAutomotiveMailRepo,
                                BusinessBankingMailRepo businessBankingMailRepo, BusinessFashionMailRepo businessFashionMailRepo,
                                BusinessGlobalMailRepo businessGlobalMailRepo, BusinessHealthCareMailRepo businessHealthCareMailRepo,
                                BusinessHotelMailRepo businessHotelMailRepo, BusinessLawMailRepo businessLawMailRepo,
                                BusinessMediaMailRepo businessMediaMailRepo, BusinessTechMailRepo businessTechMailRepo,
                        AppleRepo appleRepo, GmailRepo gmailRepo, GovRepo govRepo, InvalidRepo invalidRepo, LspRepo lspRepo,
                        OtherRepo otherRepo, RoleRepo roleRepo, VietnamRepo vietnamRepo, YahooEmailRepo yahooEmailRepo,
                        EmailRepo emailRepo, EduRepo eduRepo, OrgRepo orgRepo, SaltluxRepo saltluxRepo, TamRepo tamRepo) {
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
        this.appleRepo = appleRepo;
        this.gmailRepo = gmailRepo;
        this.govRepo = govRepo;
        this.invalidRepo = invalidRepo;
        this.otherRepo = otherRepo;
        this.roleRepo = roleRepo;
        this.vietnamRepo = vietnamRepo;
        this.yahooEmailRepo = yahooEmailRepo;
        this.eduRepo = eduRepo;
        this.orgRepo = orgRepo;
        this.saltluxRepo = saltluxRepo;
        this.tamRepo = tamRepo;
    }

    @Override
    public void run() {
        filterAgain(this.lstDataToSave);
    }

    @Transactional
    protected void firstFilterMail(List<IdEmail> idEmails) {
        filterAllMailService.filterFirstMail(idEmails);
    }

    @Transactional
    protected void filterBusinessMail(List<BusinessEmailModel> businessEmailModels) {
        List<BusinessEmailModel> businessEmailAfterFilterLaw = filterBusinessByType(businessEmailModels, BusinessMailType.LAW);
        List<BusinessEmailModel> businessEmailAfterFilterBanking = filterBusinessByType(businessEmailAfterFilterLaw, BusinessMailType.BANKING);
        List<BusinessEmailModel> businessEmailAfterFilterTech = filterBusinessByType(businessEmailAfterFilterBanking, BusinessMailType.TECH);
        List<BusinessEmailModel> businessEmailAfterFilterAutomotive = filterBusinessByType(businessEmailAfterFilterTech, BusinessMailType.AUTOMOTIVE);
        List<BusinessEmailModel> businessEmailAfterFilterHeathCare = filterBusinessByType(businessEmailAfterFilterAutomotive, BusinessMailType.HEALTH_CARE);
        List<BusinessEmailModel> businessEmailAfterFilterMedia = filterBusinessByType(businessEmailAfterFilterHeathCare, BusinessMailType.MEDIA);
        List<BusinessEmailModel> businessEmailAfterFilterGlobal = filterBusinessByType(businessEmailAfterFilterMedia, BusinessMailType.GLOBAL);
        List<BusinessEmailModel> businessEmailAfterFilterHotel = filterBusinessByType(businessEmailAfterFilterGlobal, BusinessMailType.HOTEL);
        List<BusinessEmailModel> businessEmailAfterFilterFashion = filterBusinessByType(businessEmailAfterFilterHotel, BusinessMailType.FASHION);
        if (Objects.nonNull(businessEmailAfterFilterFashion) && businessEmailAfterFilterFashion.size() > 0) {
            List<IdEmail> businessEmailModelsRest = AppUtil.mapAll(businessEmailAfterFilterFashion, IdEmail.class);
            emailRepo.saveAll(businessEmailModelsRest);
        }
        businessEmailRepo.deleteAllByIdInBatch(businessEmailModels.stream().map(BusinessEmailModel::getMemberId).collect(Collectors.toList()));
        System.out.println("Done filter business mail in Thread: " + Thread.currentThread().getName());
    }

    @Transactional
    public void saveToIdEmail(List<? extends BaseModel> baseModels) {
        List<IdEmail> idEmailList = AppUtil.mapAll(baseModels, IdEmail.class);
        emailRepo.saveAll(idEmailList);
        filterAllMailService.deleteByTypeFirstStep(baseModels);
        System.out.println("Save done in thread: " + Thread.currentThread().getName());
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
    public void splitMail(List<? extends BaseModel> list) {
        List<? extends BaseModel> listOut = list.stream().map(item -> {
            String mailSplit = FilterUtil.splitByMail(((BaseModel) item).getMemberPrimaryEmail());
            ((BaseModel) item).setMailName(mailSplit);
            return item;
        }).collect(Collectors.toList());
        updateMailName(list);
        System.out.println("DONE WHEN RENAME BUSINESS MAIL IN THREAD: " + Thread.currentThread().getName());
    }

    @Transactional(dontRollbackOn = Exception.class)
    public void filterAgain(List<? extends BaseModel> list) {
        if (list.size() == 0) {
            return;
        }
        List<? extends BaseModel> list1 = filterAllMailService.filterFirstStepMailAgain(list);
        List<? extends BaseModel> list2 = businessEmailService.filterFromBusinessAgain(list1);
        if (Objects.nonNull(list2) && list2.size() > 0) {
            List<BusinessEmailModel> businessEmailModelList = AppUtil.mapAll(list2, BusinessEmailModel.class);
            businessEmailRepo.saveAll(businessEmailModelList);
        }
        tamRepo.deleteAllByIdInBatch(list.stream().map(BaseModel::getMemberId).collect(Collectors.toList()));
        System.out.println("Done filter again = " + Thread.currentThread().getName());
    }

    @Transactional(dontRollbackOn = Exception.class)
    public void updateMailName(List<? extends BaseModel> list) {
        if (list.size() == 0) {
            return;
        }
        if (list.get(0) instanceof BusinessAutomotiveModel) {
            List<BusinessAutomotiveModel> businessAutomotiveModels = AppUtil.mapAll(list, BusinessAutomotiveModel.class);
            businessAutomotiveMailRepo.saveAll(businessAutomotiveModels);
        } else if (list.get(0) instanceof BusinessBankingModel) {
            List<BusinessBankingModel> businessBankingModels = AppUtil.mapAll(list, BusinessBankingModel.class);
            businessBankingMailRepo.saveAll(businessBankingModels);
        } else if (list.get(0) instanceof BusinessEmailModel) {
            List<BusinessEmailModel> businessBankingModels = AppUtil.mapAll(list, BusinessEmailModel.class);
            businessEmailRepo.saveAll(businessBankingModels);
        } else if (list.get(0) instanceof  BusinessFashionModel) {
            List<BusinessFashionModel> businessBankingModels = AppUtil.mapAll(list, BusinessFashionModel.class);
            businessFashionMailRepo.saveAll(businessBankingModels);
        } else if (list.get(0) instanceof BusinessGlobalModel) {
            List<BusinessGlobalModel> businessBankingModels = AppUtil.mapAll(list, BusinessGlobalModel.class);
            businessGlobalMailRepo.saveAll(businessBankingModels);
        } else if (list.get(0) instanceof BusinessHealthCareModel) {
            List<BusinessHealthCareModel> businessBankingModels = AppUtil.mapAll(list, BusinessHealthCareModel.class);
            businessHealthCareMailRepo.saveAll(businessBankingModels);
        } else if (list.get(0) instanceof BusinessHotelModel) {
            List<BusinessHotelModel> businessBankingModels = AppUtil.mapAll(list, BusinessHotelModel.class);
            businessHotelMailRepo.saveAll(businessBankingModels);
        } else if (list.get(0) instanceof BusinessLawModel) {
            List<BusinessLawModel> businessBankingModels = AppUtil.mapAll(list, BusinessLawModel.class);
            businessLawMailRepo.saveAll(businessBankingModels);
        } else if (list.get(0) instanceof BusinessMediaModel) {
            List<BusinessMediaModel> businessBankingModels = AppUtil.mapAll(list, BusinessMediaModel.class);
            businessMediaMailRepo.saveAll(businessBankingModels);
        } else if (list.get(0) instanceof BusinessTechModel) {
            List<BusinessTechModel> businessBankingModels = AppUtil.mapAll(list, BusinessTechModel.class);
            businessTechMailRepo.saveAll(businessBankingModels);
        } else if (list.get(0) instanceof LspModel) {
            List<LspModel> lspModels = AppUtil.mapAll(list, LspModel.class);
            lspRepo.saveAll(lspModels);
        } else if (list.get(0) instanceof OrgModel) {
            List<OrgModel> orgModels = AppUtil.mapAll(list, OrgModel.class);
            orgRepo.saveAll(orgModels);
        } else if (list.get(0) instanceof GovModel) {
            List<GovModel> govModels = AppUtil.mapAll(list, GovModel.class);
            govRepo.saveAll(govModels);
        } else if (list.get(0) instanceof EduModel) {
            List<EduModel> eduModels = AppUtil.mapAll(list, EduModel.class);
            eduRepo.saveAll(eduModels);
        } else {
            System.out.println("=============Oi doi oi=================");
        }
    }
}
