package com.saltlux.tool.filter.tool.service;

import com.saltlux.tool.filter.tool.model.*;
import com.saltlux.tool.filter.tool.repo.*;
import com.saltlux.tool.filter.tool.util.AppUtil;
import com.saltlux.tool.filter.tool.util.FilterUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
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
public class FilterAllMailService implements Runnable, IFilterService{

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
    private List<IdEmail> idEmailsToDelete = new ArrayList<>();

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

    @Autowired
    private BusinessEmailRepo businessEmailRepo;

    public FilterAllMailService(AppleRepo appleRepo, GmailRepo gmailRepo, GovRepo govRepo, InvalidRepo invalidRepo, LspRepo lspRepo,
                                OtherRepo otherRepo, RoleRepo roleRepo, VietnamRepo vietnamRepo, YahooEmailRepo yahooEmailRepo, EmailRepo emailRepo) {
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
    }

    @Override
    public void run() {
        filter(idEmails);
    }

    @Override
    @Transactional
    public void filter(List<IdEmail> idEmails) {
        try {
            List<IdEmail> listAfterFilterGmail = filterGmail(idEmails);
            List<IdEmail> listAfterFilterYahooMail = filterYahooMail(listAfterFilterGmail);
            List<IdEmail> listAfterFilterOtherMail = filterOtherMail(listAfterFilterYahooMail);
            List<IdEmail> listAfterFilterInvalidMail = filterInvalidMail(listAfterFilterOtherMail);
            List<IdEmail> listAfterFilterAppleMail = filterAppleMail(listAfterFilterInvalidMail);
            List<IdEmail> listAfterFilterGovMail = filterGovMail(listAfterFilterAppleMail);
            List<IdEmail> listAfterFilterLspMail = filterLspMail(listAfterFilterGovMail);
            List<IdEmail> listAfterFilterRoleMail = filterRoleMail(listAfterFilterLspMail);
            List<IdEmail> listAfterFilterVietnamMail = filterVietnamMail(listAfterFilterRoleMail);
            if (Objects.nonNull(listAfterFilterVietnamMail) && listAfterFilterVietnamMail.size() > 0) {
                List<BusinessEmailModel> businessEmailModels = AppUtil.mapAll(listAfterFilterVietnamMail, BusinessEmailModel.class);
//                this.businessEmailModels.addAll(businessEmailModels);
                businessEmailRepo.saveAll(businessEmailModels);
            }
            emailRepo.deleteAllByIdInBatch(idEmails.stream().map(IdEmail::getMemberId).collect(Collectors.toList()));
            System.out.println("Done in thread " + Thread.currentThread().getName());
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Transactional
    List<IdEmail> filterAppleMail(List<IdEmail> idEmails) {
        if (Objects.isNull(idEmails) || idEmails.size() == 0) {
            return null;
        }
        Map<Boolean, List<IdEmail>> numbersByIsPositive = idEmails.stream()
                .collect(Collectors.groupingBy(mail -> (Objects.nonNull(mail.getMemberPrimaryEmail()) && FilterUtil.filterAppleMail(mail.getMemberPrimaryEmail()))));
        List<IdEmail> idEmailsOut = numbersByIsPositive.get(true);
        if (Objects.nonNull(idEmailsOut) && idEmailsOut.size() > 0) {
            List<AppleModel> otherModels = AppUtil.mapAll(numbersByIsPositive.get(true), AppleModel.class);
//            this.appleModels.addAll(otherModels);
            appleRepo.saveAll(otherModels);
        }
        return numbersByIsPositive.get(false);
    }

    @Transactional
    List<IdEmail> filterGmail(List<IdEmail> idEmails) {
        if (Objects.isNull(idEmails) || idEmails.size() == 0) {
            return null;
        }
        Map<Boolean, List<IdEmail>> numbersByIsPositive = idEmails.stream()
                .collect(Collectors.groupingBy(mail -> (Objects.nonNull(mail.getMemberPrimaryEmail()) && FilterUtil.filterGmail(mail.getMemberPrimaryEmail()))));
        List<IdEmail> idEmailsOut = numbersByIsPositive.get(true);
        if (Objects.nonNull(idEmailsOut) && idEmailsOut.size() > 0) {
            List<GmailModel> gmailModels = AppUtil.mapAll(numbersByIsPositive.get(true), GmailModel.class);
//            this.gmailModels.addAll(gmailModels);
            gmailRepo.saveAll(gmailModels);
        }
        return numbersByIsPositive.get(false);
    }

    @Transactional
    List<IdEmail> filterGovMail(List<IdEmail> idEmails) {
        if (Objects.isNull(idEmails) || idEmails.size() == 0) {
            return null;
        }
        Map<Boolean, List<IdEmail>> numbersByIsPositive = idEmails.stream()
                .collect(Collectors.groupingBy(mail -> (Objects.nonNull(mail.getMemberPrimaryEmail()) && FilterUtil.filterGOVMail(mail.getMemberPrimaryEmail()))));
        List<IdEmail> idEmailsOut = numbersByIsPositive.get(true);
        if (Objects.nonNull(idEmailsOut) && idEmailsOut.size() > 0) {
            List<GovModel> gmailModels = AppUtil.mapAll(idEmails, GovModel.class);
//            this.govModels.addAll(gmailModels);
            this.govRepo.saveAll(gmailModels);
        }
        return numbersByIsPositive.get(false);
    }

    @Transactional
    List<IdEmail> filterInvalidMail(List<IdEmail> idEmails) {
        if (Objects.isNull(idEmails) || idEmails.size() == 0) {
            return null;
        }
        Map<Boolean, List<IdEmail>> numbersByIsPositive = idEmails.stream()
                .collect(Collectors.groupingBy(mail -> (Objects.nonNull(mail.getMemberPrimaryEmail()) && FilterUtil.filterInvalidMail(mail.getMemberPrimaryEmail()))));
        List<IdEmail> idEmailsOut = numbersByIsPositive.get(true);
        if (Objects.nonNull(idEmailsOut) && idEmailsOut.size() > 0) {
            List<InvalidModel> gmailModels = AppUtil.mapAll(numbersByIsPositive.get(true), InvalidModel.class);
//            this.invalidModels.addAll(gmailModels);
            invalidRepo.saveAll(gmailModels);
        }
        return numbersByIsPositive.get(false);
    }

    @Transactional
    List<IdEmail> filterLspMail(List<IdEmail> idEmails) {
        if (Objects.isNull(idEmails) || idEmails.size() == 0) {
            return null;
        }
        Map<Boolean, List<IdEmail>> numbersByIsPositive = idEmails.stream()
                .collect(Collectors.groupingBy(mail -> (Objects.nonNull(mail.getMemberPrimaryEmail()) && FilterUtil.filterLSPMail(mail.getMemberPrimaryEmail()))));
        List<IdEmail> idEmailsOut = numbersByIsPositive.get(true);
        if (Objects.nonNull(idEmailsOut) && idEmailsOut.size() > 0) {
            List<LspModel> gmailModels = AppUtil.mapAll(numbersByIsPositive.get(true), LspModel.class);
//            this.lspModels.addAll(gmailModels);
            lspRepo.saveAll(gmailModels);
        }
        return numbersByIsPositive.get(false);
    }

    @Transactional
    List<IdEmail> filterOtherMail(List<IdEmail> idEmails) {
        if (Objects.isNull(idEmails) || idEmails.size() == 0) {
            return null;
        }
        Map<Boolean, List<IdEmail>> numbersByIsPositive = idEmails.stream()
                .collect(Collectors.groupingBy(mail -> (Objects.nonNull(mail.getMemberPrimaryEmail()) && FilterUtil.filterOtherMail(mail.getMemberPrimaryEmail()))));
        List<IdEmail> idEmailsOut = numbersByIsPositive.get(true);
        if (Objects.nonNull(idEmailsOut) && idEmailsOut.size() > 0) {
            List<OtherModel> otherModels = AppUtil.mapAll(numbersByIsPositive.get(true), OtherModel.class);
//            this.otherModels.addAll(otherModels);
            otherRepo.saveAll(otherModels);
        }
        return numbersByIsPositive.get(false);
    }

    @Transactional
    List<IdEmail> filterRoleMail(List<IdEmail> idEmails) {
        if (Objects.isNull(idEmails) || idEmails.size() == 0) {
            return null;
        }
        Map<Boolean, List<IdEmail>> numbersByIsPositive = idEmails.stream()
                .collect(Collectors.groupingBy(mail -> (Objects.nonNull(mail.getMemberPrimaryEmail()) && FilterUtil.filterRoleMail(mail.getMemberPrimaryEmail()))));
        List<IdEmail> idEmailsOut = numbersByIsPositive.get(true);
        if (Objects.nonNull(idEmailsOut) && idEmailsOut.size() > 0) {
            List<RoleModel> otherModels = AppUtil.mapAll(numbersByIsPositive.get(true), RoleModel.class);
//            this.roleModels.addAll(otherModels);
            roleRepo.saveAll(otherModels);
        }
        return numbersByIsPositive.get(false);
    }

    @Transactional
    List<IdEmail> filterVietnamMail(List<IdEmail> idEmails) {
        if (Objects.isNull(idEmails) || idEmails.size() == 0) {
            return null;
        }
        Map<Boolean, List<IdEmail>> numbersByIsPositive = idEmails.stream()
                .collect(Collectors.groupingBy(mail -> (Objects.nonNull(mail.getMemberPrimaryEmail()) && FilterUtil.filterVietnamMail(mail.getMemberPrimaryEmail()))));
        List<IdEmail> idEmailsOut = numbersByIsPositive.get(true);
        if (Objects.nonNull(idEmailsOut) && idEmailsOut.size() > 0) {
            List<VietnamModel> otherModels = AppUtil.mapAll(numbersByIsPositive.get(true), VietnamModel.class);
//            this.vietnamModels.addAll(otherModels);
            vietnamRepo.saveAll(otherModels);
        }
        return numbersByIsPositive.get(false);
    }

    @Transactional
    List<IdEmail> filterYahooMail(List<IdEmail> idEmails) {
        if (Objects.isNull(idEmails) || idEmails.size() == 0) {
            return null;
        }
        Map<Boolean, List<IdEmail>> numbersByIsPositive = idEmails.stream()
                .collect(Collectors.groupingBy(mail -> (Objects.nonNull(mail.getMemberPrimaryEmail()) && FilterUtil.filterYahooMail(mail.getMemberPrimaryEmail()))));
        List<IdEmail> idEmailsOut = numbersByIsPositive.get(true);
        if (Objects.nonNull(idEmailsOut) && idEmailsOut.size() > 0) {
            List<YahooMailModel> yahooMailModels = AppUtil.mapAll(numbersByIsPositive.get(true), YahooMailModel.class);
//            this.yahooMailModels.addAll(yahooMailModels);
            yahooEmailRepo.saveAll(yahooMailModels);
        }
        return numbersByIsPositive.get(false);
    }

    @Deprecated
    @Transactional
    public void deleteAfterFilter() {
        if (Objects.nonNull(idEmailsToDelete) && idEmailsToDelete.size() > 0) {
//            CompletableFuture.delayedExecutor(5, TimeUnit.SECONDS).execute(() -> {
//                emailRepo.deleteAllByIdInBatch(idEmailsToDelete.stream().map(IdEmail::getMemberId).collect(Collectors.toList()));
//                System.out.println("Delete Success " + idEmailsToDelete.size() + " row");
//            });
        }
    }
}
