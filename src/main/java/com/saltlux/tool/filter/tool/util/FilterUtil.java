package com.saltlux.tool.filter.tool.util;

import com.saltlux.tool.filter.tool.dto.LinkedInAccountDto;

import java.util.Objects;

public class FilterUtil {

    public static boolean filterBusinessEmail(String source) {
        if (Objects.isNull(source)) {
            return false;
        }
        return !filterYahooMail(source) && !filterGmail(source) && !filterAppleMail(source) && !filterOtherMail(source)
                && !filterRoleMail(source) && !filterInvalidMail(source) && !filterVietnamMail(source) && !filterLSPMail(source)
                && !filterGOVMail(source);
    }

    public static boolean filterYahooMail(String source) {
        if (Objects.isNull(source)) {
            return false;
        }
        return checkWithOr(source, FilterConstraints.YAHOOS);
    }

    public static boolean filterGmail(String source) {
        if (Objects.isNull(source)) {
            return false;
        }
        return checkWithOr(source, FilterConstraints.GMAILS);
    }

    public static boolean filterAppleMail(String source) {
        if (Objects.isNull(source)) {
            return false;
        }
        return checkWithOr(source, FilterConstraints.APPLES);
    }

    public static boolean filterOtherMail(String source) {
        if (Objects.isNull(source)) {
            return false;
        }
        return checkWithOr(source, FilterConstraints.OTHERS);
    }

    public static boolean filterRoleMail(String source) {
        if (Objects.isNull(source)) {
            return false;
        }
        return checkStartWithOr(source, FilterConstraints.ROLES);
    }

    public static boolean filterInvalidMail(String source) {
        if (Objects.isNull(source)) {
            return false;
        }
        return checkWithOr(source, FilterConstraints.INVALIDS);
    }

    public static boolean filterVietnamMail(String source) {
        if (Objects.isNull(source)) {
            return false;
        }
        return checkWithOr(source, FilterConstraints.VIETNAMS);
    }

    public static boolean filterLSPMail(String source) {
        if (Objects.isNull(source)) {
            return false;
        }
        return checkWithOr(source, FilterConstraints.LSPS);
    }

    public static boolean filterGOVMail(String source) {
        if (Objects.isNull(source)) {
            return false;
        }
        return checkWithOr(source, FilterConstraints.GOVS);
    }

    public static boolean filterBusinessGlobalMail(String source) {
        if (Objects.isNull(source)) {
            return false;
        }
        return checkWithOr(source, FilterConstraints.BUSINESS_GLOBALS);
    }

    public static boolean filterBusinessMediaMail(String source) {
        if (Objects.isNull(source)) {
            return false;
        }
        return checkWithOr(source, FilterConstraints.BUSINESS_MEDIAS);
    }

    public static boolean filterBusinessAutomotiveMail(String source) {
        if (Objects.isNull(source)) {
            return false;
        }
        return checkWithOr(source, FilterConstraints.BUSINESS_AUTOMOTIVES);
    }

    public static boolean filterBusinessHotelMail(String source) {
        if (Objects.isNull(source)) {
            return false;
        }
        return checkWithOr(source, FilterConstraints.BUSINESS_HOTELS);
    }

    public static boolean filterBusinessFashionMail(String source) {
        if (Objects.isNull(source)) {
            return false;
        }
        return checkWithOr(source, FilterConstraints.BUSINESS_FASHIONS);
    }

    public static boolean filterBusinessBankingMail(String source) {
        if (Objects.isNull(source)) {
            return false;
        }
        return checkWithOr(source, FilterConstraints.BUSINESS_BANKINGS);
    }

    public static boolean filterBusinessBusinessTechMail(String source) {
        if (Objects.isNull(source)) {
            return false;
        }
        return checkWithOr(source, FilterConstraints.BUSINESS_TECHS);
    }

    public static boolean filterBusinessLawMail(String source) {
        if (Objects.isNull(source)) {
            return false;
        }
        return checkWithOr(source, FilterConstraints.BUSINESS_LAWS);
    }

    public static boolean filterBusinessHealthCareMail(String source) {
        if (Objects.isNull(source)) {
            return false;
        }
        return checkWithOr(source, FilterConstraints.BUSINESS_HEALTH_CARES);
    }

    public static boolean filterBusinessByType(String source, BusinessMailType businessMailType) {
        if (Objects.isNull(source)) {
            return false;
        }
        switch (businessMailType) {
            case GLOBAL:
                return filterBusinessGlobalMail(source);
            case MEDIA:
                return filterBusinessMediaMail(source);
            case AUTOMOTIVE:
                return filterBusinessAutomotiveMail(source);
            case HOTEL:
                return filterBusinessHotelMail(source);
            case FASHION:
                return filterBusinessFashionMail(source);
            case BANKING:
                return filterBusinessBankingMail(source);
            case TECH:
                return filterBusinessBusinessTechMail(source);
            case LAW:
                return filterBusinessLawMail(source);
            case HEALTH_CARE:
                return filterBusinessHealthCareMail(source);
            default:
                System.out.println("Type False. Filter Fail");
                return false;
        }
    }

    public static boolean filterJSONFile(LinkedInAccountDto linkedInAccount) {
        return (Objects.nonNull(linkedInAccount.getEmails()) && linkedInAccount.getEmails().size() > 0)
                || Objects.nonNull(linkedInAccount.getWork_email());
    }

    private static boolean checkWithOr(String source, String[] des) {
        if (Objects.isNull(source) || des.length == 0) {
            return false;
        }
        boolean check = false;
        for (String data : des) {
            if (AppUtil.containIgnoreCase(source, data)) {
                check = true;
                break;
            }
        }
        return check;
    }

    public static void main(String[] args) {
        System.out.println(checkWithOr("techlaw1@techlawrecruiting.com", FilterConstraints.BUSINESS_LAWS));
    }

    private static boolean checkStartWithOr(String source, String[] des) {
        if (Objects.isNull(source) || des.length == 0) {
            return false;
        }
        int check = 0;
        for (String data : des) {
            if (AppUtil.startWithIgnoreCase(source, data)) {
                check += 1;
            }
        }
        return check > 0;
    }


}
