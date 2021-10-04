package com.saltlux.tool.filter.tool.service;

import com.saltlux.tool.filter.tool.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class SaveAllService {

    @Autowired
    private FilterAllMailService filterAllMailService;

    @Autowired
    private ApplicationContext applicationContext;

    public void saveAllMail() throws InterruptedException {
        List<GmailModel> gmailModelList = filterAllMailService.getGmailModels();
        if (Objects.nonNull(gmailModelList) && gmailModelList.size() > 0) {
            EmailService emailService = applicationContext.getBean(EmailService.class);
            emailService.setGmailModels(gmailModelList);
            Thread thread3 = new Thread(emailService);
            thread3.start();
        }

        List<YahooMailModel> yahooMailModels = filterAllMailService.getYahooMailModels();
        if (Objects.nonNull(yahooMailModels) && yahooMailModels.size() > 0) {
            YahooMailService yahooMailService = applicationContext.getBean(YahooMailService.class);
            yahooMailService.setYahooMailModels(yahooMailModels);
            Thread thread10 = new Thread(yahooMailService);
            thread10.start();
        }

        List<OtherModel> otherModels = filterAllMailService.getOtherModels();
        if (Objects.nonNull(otherModels) && otherModels.size() > 0) {
            OthersMailService othersMailService = applicationContext.getBean(OthersMailService.class);
            othersMailService.setOtherModels(otherModels);
            Thread thread7 = new Thread(othersMailService);
            thread7.start();
        }

        List<InvalidModel> invalidModels = filterAllMailService.getInvalidModels();
        if (Objects.nonNull(invalidModels) && invalidModels.size() > 0) {
            InvalidMailService invalidMailService = applicationContext.getBean(InvalidMailService.class);
            invalidMailService.setInvalidModels(invalidModels);
            Thread thread5 = new Thread(invalidMailService);
            thread5.start();
        }

        List<AppleModel> appleModels = filterAllMailService.getAppleModels();
        if (Objects.nonNull(appleModels) && appleModels.size() > 0) {
            AppleMailService appleMailService = applicationContext.getBean(AppleMailService.class);
            appleMailService.setAppleModels(appleModels);
            Thread thread1 = new Thread(appleMailService);
            thread1.start();
        }

        List<GovModel> govModels = filterAllMailService.getGovModels();
        if (Objects.nonNull(govModels) && govModels.size() > 0) {
            GovMailService govMailService = applicationContext.getBean(GovMailService.class);
            govMailService.setGovModels(govModels);
            Thread thread4 = new Thread(govMailService);
            thread4.start();
        }

        List<LspModel> lspModels = filterAllMailService.getLspModels();
        if (Objects.nonNull(lspModels) && lspModels.size() > 0) {
            LspMailService lspMailService = applicationContext.getBean(LspMailService.class);
            lspMailService.setLspModels(lspModels);
            Thread thread6 = new Thread(lspMailService);
            thread6.start();
        }

        List<RoleModel> roleModels = filterAllMailService.getRoleModels();
        if (Objects.nonNull(yahooMailModels) && yahooMailModels.size() > 0) {
            RoleMailService roleMailService = applicationContext.getBean(RoleMailService.class);
            roleMailService.setRoleModels(roleModels);
            Thread thread8 = new Thread(roleMailService);
            thread8.start();
        }

        List<VietnamModel> vietnamModels = filterAllMailService.getVietnamModels();
        if (Objects.nonNull(vietnamModels) && vietnamModels.size() > 0) {
            VietnamMailService vietnamMailService = applicationContext.getBean(VietnamMailService.class);
            vietnamMailService.setVietnamModels(vietnamModels);
            Thread thread9 = new Thread(vietnamMailService);
            thread9.start();
        }

        List<BusinessEmailModel> businessEmailModels = filterAllMailService.getBusinessEmailModels();
        if (Objects.nonNull(businessEmailModels) && businessEmailModels.size() > 0) {
            BusinessEmailService businessEmailService = applicationContext.getBean(BusinessEmailService.class);
            businessEmailService.setBusinessEmailModels(businessEmailModels);
            Thread thread2 = new Thread(businessEmailService);
            thread2.start();
        }
//        Thread.sleep(2000);
        filterAllMailService.deleteAfterFilter();
    }
}
