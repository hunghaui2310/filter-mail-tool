package com.saltlux.tool.filter.tool.util;

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

    private static boolean checkWithOr(String source, String[] des) {
        if (Objects.isNull(source) || des.length == 0) {
            return false;
        }
        int check = 0;
        for (String data : des) {
            if (AppUtil.containIgnoreCase(source, data)) {
                check += 1;
            }
        }
        return check > 0;
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
