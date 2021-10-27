package com.saltlux.tool.filter.tool.service;

import com.saltlux.tool.filter.tool.model.*;
import com.saltlux.tool.filter.tool.repo.*;
import com.saltlux.tool.filter.tool.util.AppUtil;
import com.saltlux.tool.filter.tool.util.FilterConstraints;
import com.saltlux.tool.filter.tool.util.FilterUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Setter
@Getter
public class FilterAllMailService implements Runnable, IFilterService {

    private Integer page;
    private List<AppleModel> appleModels = new ArrayList<>();
    private List<BusinessEmailModel> businessEmailModels = new ArrayList<>();
    private List<GmailModel> gmailModels = new ArrayList<>();
    private List<GovModel> govModels = new ArrayList<>();
    private List<InvalidModel> invalidModels = new ArrayList<>();
    private List<LspModel> lspModels = new ArrayList<>();
    private List<OtherModel> otherModels = new ArrayList<>();
    private List<RoleModel> roleModels = new ArrayList<>();
    private List<VietnamModel> vietnamModels = new ArrayList<>();
    private List<YahooMailModel> yahooMailModels = new ArrayList<>();
    private List<IdEmail> idEmails = new ArrayList<>();
//    private List<IdEmail> idEmailsToDelete = new ArrayList<>();

    private final AppleRepo appleRepo;
    private final GmailRepo gmailRepo;
    private final GovRepo govRepo;
    private final InvalidRepo invalidRepo;
    private final LspRepo lspRepo;
    private final OtherRepo otherRepo;
    private final RoleRepo roleRepo;
    private final VietnamRepo vietnamRepo;
    private final YahooEmailRepo yahooEmailRepo;
    private final EmailRepo emailRepo;
    private final EduRepo eduRepo;
    private final OrgRepo orgRepo;
    private final SaltluxRepo saltluxRepo;

    @Autowired
    private BusinessEmailRepo businessEmailRepo;

    public FilterAllMailService(AppleRepo appleRepo, GmailRepo gmailRepo, GovRepo govRepo, InvalidRepo invalidRepo, LspRepo lspRepo,
                                OtherRepo otherRepo, RoleRepo roleRepo, VietnamRepo vietnamRepo, YahooEmailRepo yahooEmailRepo,
                                EmailRepo emailRepo, EduRepo eduRepo, OrgRepo orgRepo, SaltluxRepo saltluxRepo) {
        this.appleRepo = appleRepo;
        this.gmailRepo = gmailRepo;
        this.govRepo = govRepo;
        this.invalidRepo = invalidRepo;
        this.lspRepo = lspRepo;
        this.otherRepo = otherRepo;
        this.roleRepo = roleRepo;
        this.vietnamRepo = vietnamRepo;
        this.yahooEmailRepo = yahooEmailRepo;
        this.emailRepo = emailRepo;
        this.eduRepo = eduRepo;
        this.orgRepo = orgRepo;
        this.saltluxRepo = saltluxRepo;
    }

    @Override
    public void run() {
        filter(idEmails);
    }

    @Override
    @Transactional
    public void filter(List<IdEmail> idEmails) {
        filterFirstMail(idEmails);
    }

    @Transactional
    public void filterFirstMail(List<IdEmail> idEmails) {
        try {
            List<IdEmail> listAfterFilterOrgMail = filterMailByType(idEmails, FilterConstraints.MailType.INVALID);
            List<IdEmail> listAfterFilterEduMail = filterMailByType(listAfterFilterOrgMail, FilterConstraints.MailType.YAHOO);
            List<IdEmail> listAfterFilterSaltluxMail = filterMailByType(listAfterFilterEduMail, FilterConstraints.MailType.GMAIL);
            List<IdEmail> listAfterFilterGmail = filterMailByType(listAfterFilterSaltluxMail, FilterConstraints.MailType.APPLE);
            List<IdEmail> listAfterFilterYahooMail = filterMailByType(listAfterFilterGmail, FilterConstraints.MailType.OTHER);
            List<IdEmail> listAfterFilterOtherMail = filterMailByType(listAfterFilterYahooMail, FilterConstraints.MailType.ROLE);
            List<IdEmail> listAfterFilterInvalidMail = filterMailByType(listAfterFilterOtherMail, FilterConstraints.MailType.VIETNAM);
            List<IdEmail> listAfterFilterAppleMail = filterMailByType(listAfterFilterInvalidMail, FilterConstraints.MailType.GOV);
            List<IdEmail> listAfterFilterGovMail = filterMailByType(listAfterFilterAppleMail, FilterConstraints.MailType.ORG);
            List<IdEmail> listAfterFilterLspMail = filterMailByType(listAfterFilterGovMail, FilterConstraints.MailType.EDU);
            List<IdEmail> listAfterFilterRoleMail = filterMailByType(listAfterFilterLspMail, FilterConstraints.MailType.SALTLUX);
            List<IdEmail> listAfterFilterVietnamMail = filterMailByType(listAfterFilterRoleMail, FilterConstraints.MailType.LSP);
            if (Objects.nonNull(listAfterFilterVietnamMail) && listAfterFilterVietnamMail.size() > 0) {
                List<BusinessEmailModel> businessEmailModels = AppUtil.mapAll(listAfterFilterVietnamMail, BusinessEmailModel.class);
                businessEmailRepo.saveAll(businessEmailModels);
            }
            emailRepo.deleteAllByIdInBatch(idEmails.stream().map(IdEmail::getMemberId).collect(Collectors.toList()));
            System.out.println("Done first filter in thread " + Thread.currentThread().getName());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Transactional
    public List<IdEmail> filterMailByType(List<IdEmail> idEmails, FilterConstraints.MailType mailType) {
        if (Objects.isNull(idEmails) || idEmails.size() == 0) {
            return null;
        }
        Map<Boolean, List<IdEmail>> numbersByIsPositive = idEmails.stream()
                .collect(Collectors.groupingBy(mail -> (Objects.nonNull(mail.getMemberPrimaryEmail()) && FilterUtil.filterMailByType(mail.getMemberPrimaryEmail(), mailType))));
        List<IdEmail> idEmailList = numbersByIsPositive.get(true);
        if (Objects.nonNull(idEmailList) && idEmailList.size() > 0) {
            switch (mailType) {
                case YAHOO:
                    List<YahooMailModel> yahooMailModels = AppUtil.mapAll(idEmailList, YahooMailModel.class);
                    yahooEmailRepo.saveAll(yahooMailModels);
                    break;
                case GMAIL:
                    List<GmailModel> gmailModels = AppUtil.mapAll(idEmailList, GmailModel.class);
                    gmailRepo.saveAll(gmailModels);
                    break;
                case APPLE:
                    List<AppleModel> appleModels = AppUtil.mapAll(idEmailList, AppleModel.class);
                    appleRepo.saveAll(appleModels);
                    break;
                case OTHER:
                    List<OtherModel> otherModels = AppUtil.mapAll(idEmailList, OtherModel.class);
                    otherRepo.saveAll(otherModels);
                    break;
                case ROLE:
                    List<RoleModel> roleModels = AppUtil.mapAll(idEmailList, RoleModel.class);
                    roleRepo.saveAll(roleModels);
                    break;
                case INVALID:
                    List<InvalidModel> invalidModels = AppUtil.mapAll(idEmailList, InvalidModel.class);
                    invalidRepo.saveAll(invalidModels);
                    break;
                case VIETNAM:
                    List<VietnamModel> vietnamModels = AppUtil.mapAll(idEmailList, VietnamModel.class);
                    vietnamRepo.saveAll(vietnamModels);
                    break;
                case LSP:
                    List<LspModel> lspModels = AppUtil.mapAll(idEmailList, LspModel.class);
                    lspRepo.saveAll(lspModels);
                    break;
                case GOV:
                    List<GovModel> govModels = AppUtil.mapAll(idEmailList, GovModel.class);
                    govRepo.saveAll(govModels);
                    break;
                case ORG:
                    List<OrgModel> orgModels = AppUtil.mapAll(idEmailList, OrgModel.class);
                    orgRepo.saveAll(orgModels);
                    break;
                case EDU:
                    List<EduModel> eduModels = AppUtil.mapAll(idEmailList, EduModel.class);
                    eduRepo.saveAll(eduModels);
                    break;
                case SALTLUX:
                    List<SaltluxModel> saltluxModels = AppUtil.mapAll(idEmailList, SaltluxModel.class);
                    saltluxRepo.saveAll(saltluxModels);
                    break;
            }
        }
        return numbersByIsPositive.get(false);
    }

    @Transactional
    public List<? extends BaseModel> filterFirstStepMailAgain(List<? extends BaseModel> idEmails) {
        try {
//            List<? extends BaseModel> baseModelsOut = null;
            List<? extends BaseModel> list1 = filterMailFirstStepByTypeAgain(idEmails, FilterConstraints.MailType.INVALID);
            List<? extends BaseModel> list2 = filterMailFirstStepByTypeAgain(list1, FilterConstraints.MailType.YAHOO);
            List<? extends BaseModel> list3 = filterMailFirstStepByTypeAgain(list2, FilterConstraints.MailType.GMAIL);
            List<? extends BaseModel> list4 = filterMailFirstStepByTypeAgain(list3, FilterConstraints.MailType.APPLE);
            List<? extends BaseModel> list5 = filterMailFirstStepByTypeAgain(list4, FilterConstraints.MailType.OTHER);
            List<? extends BaseModel> list6 = filterMailFirstStepByTypeAgain(list5, FilterConstraints.MailType.ROLE);
            List<? extends BaseModel> list7 = filterMailFirstStepByTypeAgain(list6, FilterConstraints.MailType.VIETNAM);
            List<? extends BaseModel> list8 = filterMailFirstStepByTypeAgain(list7, FilterConstraints.MailType.GOV);
            List<? extends BaseModel> list9 = filterMailFirstStepByTypeAgain(list8, FilterConstraints.MailType.ORG);
            List<? extends BaseModel> list10 = filterMailFirstStepByTypeAgain(list9, FilterConstraints.MailType.EDU);
            List<? extends BaseModel> list11 = filterMailFirstStepByTypeAgain(list10, FilterConstraints.MailType.SALTLUX);
            List<? extends BaseModel> list12 = filterMailFirstStepByTypeAgain(list11, FilterConstraints.MailType.LSP);
//            System.out.println("Done first filter in thread " + Thread.currentThread().getName());
            return list12;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Transactional
    public List<? extends BaseModel> filterMailFirstStepByTypeAgain(List<? extends BaseModel> idEmails, FilterConstraints.MailType mailType) {
        if (Objects.isNull(idEmails) || idEmails.size() == 0) {
            return null;
        }
        Map<Boolean, List<BaseModel>> numbersByIsPositive = idEmails.stream()
                .collect(Collectors.groupingBy(mail -> (Objects.nonNull(mail.getMemberPrimaryEmail()) && FilterUtil.filterMailByType(mail.getMemberPrimaryEmail(), mailType))));
        List<BaseModel> idEmailList = numbersByIsPositive.get(true);
        if (Objects.nonNull(idEmailList) && idEmailList.size() > 0) {
            switch (mailType) {
                case YAHOO:
                    List<YahooMailModel> yahooMailModels = AppUtil.mapAll(idEmailList, YahooMailModel.class);
                    yahooEmailRepo.saveAll(yahooMailModels);
                    break;
                case GMAIL:
                    List<GmailModel> gmailModels = AppUtil.mapAll(idEmailList, GmailModel.class);
                    gmailRepo.saveAll(gmailModels);
                    break;
                case APPLE:
                    List<AppleModel> appleModels = AppUtil.mapAll(idEmailList, AppleModel.class);
                    appleRepo.saveAll(appleModels);
                    break;
                case OTHER:
                    List<OtherModel> otherModels = AppUtil.mapAll(idEmailList, OtherModel.class);
                    otherRepo.saveAll(otherModels);
                    break;
                case ROLE:
                    List<RoleModel> roleModels = AppUtil.mapAll(idEmailList, RoleModel.class);
                    roleRepo.saveAll(roleModels);
                    break;
                case INVALID:
                    List<InvalidModel> invalidModels = AppUtil.mapAll(idEmailList, InvalidModel.class);
                    invalidRepo.saveAll(invalidModels);
                    break;
                case VIETNAM:
                    List<VietnamModel> vietnamModels = AppUtil.mapAll(idEmailList, VietnamModel.class);
                    vietnamRepo.saveAll(vietnamModels);
                    break;
                case LSP:
                    List<LspModel> lspModels = AppUtil.mapAll(idEmailList, LspModel.class);
                    lspRepo.saveAll(lspModels);
                    break;
                case GOV:
                    List<GovModel> govModels = AppUtil.mapAll(idEmailList, GovModel.class);
                    govRepo.saveAll(govModels);
                    break;
                case ORG:
                    List<OrgModel> orgModels = AppUtil.mapAll(idEmailList, OrgModel.class);
                    orgRepo.saveAll(orgModels);
                    break;
                case EDU:
                    List<EduModel> eduModels = AppUtil.mapAll(idEmailList, EduModel.class);
                    eduRepo.saveAll(eduModels);
                    break;
                case SALTLUX:
                    List<SaltluxModel> saltluxModels = AppUtil.mapAll(idEmailList, SaltluxModel.class);
                    saltluxRepo.saveAll(saltluxModels);
                    break;
            }
        }
        return numbersByIsPositive.get(false);
    }

    @Transactional
    public void saveAgain() {
        Pageable pageable = PageRequest.of(0, 5000);
        Page<AppleModel> page = appleRepo.findAll(pageable);
        List<BusinessEmailModel> businessEmailModelList = AppUtil.mapAll(page.getContent(), BusinessEmailModel.class);
        businessEmailRepo.saveAll(businessEmailModelList);
        appleRepo.deleteAllByIdInBatch(idEmails.stream().map(IdEmail::getMemberId).collect(Collectors.toList()));
    }

    @Deprecated
    @Transactional
    public void deleteAfterFilter() {
//        if (Objects.nonNull(idEmailsToDelete) && idEmailsToDelete.size() > 0) {
//            CompletableFuture.delayedExecutor(5, TimeUnit.SECONDS).execute(() -> {
//                emailRepo.deleteAllByIdInBatch(idEmailsToDelete.stream().map(IdEmail::getMemberId).collect(Collectors.toList()));
//                System.out.println("Delete Success " + idEmailsToDelete.size() + " row");
//            });
//        }
    }

    @Transactional(dontRollbackOn = Exception.class)
    public void deleteByTypeFirstStep(List<? extends BaseModel> baseModels) {
        List<String> ids = baseModels.stream().map(BaseModel::getMemberId).collect(Collectors.toList());
        if (ids.size() > 0) {
            if (baseModels.get(0) instanceof AppleModel) {
                appleRepo.deleteAllByIdInBatch(ids);
            } else if (baseModels.get(0) instanceof InvalidModel) {
                invalidRepo.deleteAllByIdInBatch(ids);
            } else if (baseModels.get(0) instanceof YahooMailModel) {
                yahooEmailRepo.deleteAllByIdInBatch(ids);
            } else if (baseModels.get(0) instanceof GmailModel) {
                gmailRepo.deleteAllByIdInBatch(ids);
            } else if (baseModels.get(0) instanceof OtherModel) {
                otherRepo.deleteAllByIdInBatch(ids);
            } else if (baseModels.get(0) instanceof RoleModel) {
                roleRepo.deleteAllByIdInBatch(ids);
            } else if (baseModels.get(0) instanceof VietnamModel) {
                vietnamRepo.deleteAllByIdInBatch(ids);
            } else if (baseModels.get(0) instanceof GovModel) {
                govRepo.deleteAllByIdInBatch(ids);
            } else if (baseModels.get(0) instanceof OrgModel) {
                orgRepo.deleteAllByIdInBatch(ids);
            } else if (baseModels.get(0) instanceof EduModel) {
                eduRepo.deleteAllByIdInBatch(ids);
            } else if (baseModels.get(0) instanceof SaltluxModel) {
                orgRepo.deleteAllByIdInBatch(ids);
            } else if (baseModels.get(0) instanceof LspModel) {
                lspRepo.deleteAllByIdInBatch(ids);
            }
        }
    }
}
