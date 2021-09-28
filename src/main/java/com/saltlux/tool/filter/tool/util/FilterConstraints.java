package com.saltlux.tool.filter.tool.util;

public interface FilterConstraints {

    String[] YAHOOS = new String[]{YAHOO_MAIL.YAHOO, YAHOO_MAIL.YMAIL};
    String[] GMAILS = new String[]{GMAIL.GMAIL, GMAIL.GOOGLE_GMAIL};
    String[] APPLES = new String[]{APPLE.ICLOUD, APPLE.APPLE, APPLE.MAC, APPLE.ME};
    String[] OTHERS = new String[]{OTHER.MICROSOFT, OTHER.WINDOWS_LIVE, OTHER.OUTLOOK, OTHER.LIVE,
                    OTHER.MSN, OTHER.GO, OTHER.AOL, OTHER.INBOX, OTHER.EMAIL, OTHER.MAIL, OTHER.HOTMAIL, OTHER.QQ, OTHER.REDIFF_MAIL,
                    OTHER.Q, OTHER.GMX, OTHER.ZOHO, OTHER.YANDEX, OTHER.MY_SPACE};
    String[] ROLES = new String[]{ROLE.INFO, ROLE.ADMIN, ROLE.ADMINISTRATOR, ROLE.ADMINISTRATION,
                    ROLE.MARKETING, ROLE.ACCOUNTS, ROLE.ACCOUNT, ROLE.HR, ROLE.MANAGER, ROLE.MAIL, ROLE.EMAIL, ROLE.INBOX, ROLE.SUPPORT,
                    ROLE.NEWS};
    String[] INVALIDS = new String[]{INVALID.PORN, INVALID.SEX, INVALID.NAVY, INVALID.USCG, INVALID.USAF,
                    INVALID.USMC, INVALID.POLICE, INVALID.FBI, INVALID.CIA};
    String[] VIETNAMS = new String[]{VIETNAM.VN};
    String[] LSPS = new String[]{LSP.TRANSLATION, LSP.LOCALIZE, LSP.LOCALIZATION, LSP.TRANSLATE};
    String[] GOVS = new String[]{GOV.GOV};

    interface YAHOO_MAIL {
        String YAHOO = "@yahoo.";
        String YMAIL = "@ymail.";
    }

    interface GMAIL {
        String GMAIL = "@gmail.";
        String GOOGLE_GMAIL = "@googlemail.";
    }

    interface APPLE {
        String ICLOUD = "@icloud.com";
        String APPLE = "@apple.com";
        String MAC = "@mac.com";
        String ME = "@me.com";
    }

    interface OTHER {
        String MICROSOFT = "@microsoft.com";
        String WINDOWS_LIVE = "@windowslive.";
        String OUTLOOK = "@outlook.";
        String LIVE = "@live.";
        String MSN = "@msn.";
        String GO = "@go.com";
        String AOL = "@aol.";
        String INBOX = "@inbox.";
        String EMAIL = "@email.";
        String MAIL = "@mail.com";
        String HOTMAIL = "@hotmail.";
        String QQ = "@qq.";
        String REDIFF_MAIL = "@rediffmail.";
        String Q = "@q.com";
        String GMX = "@gmx.com";
        String ZOHO = "@zoho";
        String YANDEX = "@yandex.";
        String MY_SPACE = "@myspace";
    }

    interface ROLE {
        String INFO = "info@";
        String ADMIN = "admin@";
        String ADMINISTRATOR = "administrator@";
        String ADMINISTRATION = "administration@";
        String MARKETING = "marketing@";
        String ACCOUNTS = "accounts@";
        String ACCOUNT = "account@";
        String HR = "hr@";
        String MANAGER = "manager@";
        String MAIL = "mail@";
        String EMAIL = "email@";
        String INBOX = "inbox@";
        String SUPPORT = "support@";
        String NEWS = "news@";
    }

    interface INVALID {
        String PORN = "porn";
        String SEX = "sex";
        String NAVY = "navy.mil";
        String USCG = "uscg.mil";
        String USAF = "us.af.mil";
        String USMC = "usmc.mil";
        String POLICE = "police.";
        String FBI = "fbi.";
        String CIA = "@cia.gov";
    }

    interface VIETNAM {
        String VN = ".vn";
    }

    interface LSP {
        String TRANSLATION = "translation";
        String LOCALIZE = "localize";
        String LOCALIZATION = "localization";
        String TRANSLATE = "translate";
//        String LSP = "lsp";
    }

    interface GOV {
        String GOV = ".gov";
    }
}
