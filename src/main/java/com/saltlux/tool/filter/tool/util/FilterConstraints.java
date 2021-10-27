package com.saltlux.tool.filter.tool.util;

public interface FilterConstraints {

    enum MailType {
        YAHOO, GMAIL, APPLE, OTHER, ROLE, INVALID, VIETNAM, LSP, GOV, ORG, EDU, SALTLUX
    }

    String[] YAHOOS = new String[]{YAHOO_MAIL.YAHOO, YAHOO_MAIL.YMAIL, YAHOO_MAIL.YHAOO, YAHOO_MAIL.YAHOOMAIL};
    String[] GMAILS = new String[]{GMAIL.GMAIL, GMAIL.GOOGLE_GMAIL};
    String[] APPLES = new String[]{APPLE.ICLOUD, APPLE.APPLE, APPLE.MAC, APPLE.ME};
    String[] OTHERS = new String[]{OTHER.MICROSOFT, OTHER.WINDOWSLIVE, OTHER.OUTLOOK, OTHER.LIVE,
                    OTHER.MSN, OTHER.GO, OTHER.AOL, OTHER.INBOX, OTHER.EMAIL, OTHER.MAIL, OTHER.HOTMAIL, OTHER.QQ, OTHER.REDIFFMAIL,
                    OTHER.Q, OTHER.GMX, OTHER.ZOHO, OTHER.YANDEX, OTHER.MYSPACE, OTHER.COX, OTHER._126, OTHER.HANMAIL, OTHER.DOMAIN,
                    OTHER.EARTHLINK, OTHER._139, OTHER._163, OTHER.VERIZON, OTHER.FREEMAIL, OTHER.MYNET, OTHER.SBCGLOBAL,
                    OTHER.NAVER, OTHER.DAUM, OTHER.AIM, OTHER.VODAFONE, OTHER.JUNO, OTHER.CHARTER, OTHER.SPECTRUM, OTHER.MYWAY, OTHER.CS,
                    OTHER._012, OTHER.HOTMAILL, OTHER.FREE, OTHER.EMAOIL, OTHER.COCMAST, OTHER.OTMLAIL, OTHER.TOM, OTHER.LINKEDIN,
                    OTHER._1298, OTHER.NTLWORLD, OTHER.INYOURWORLD, OTHER.HOTMAL};
    String[] ROLES = new String[]{ROLE.INFO, ROLE.ADMIN, ROLE.ADMINISTRATOR, ROLE.ADMINISTRATION,
                    ROLE.MARKETING, ROLE.ACCOUNTS, ROLE.ACCOUNT, ROLE.HR, ROLE.MANAGER, ROLE.MAIL, ROLE.EMAIL, ROLE.INBOX, ROLE.SUPPORT,
                    ROLE.NEWS};
    String[] INVALIDS = new String[]{INVALID.PORN, INVALID.SEX, INVALID.NAVY_MIL, INVALID.USCG_MIL, INVALID.US_AF_MIL,
                    INVALID.USMC_MIL, INVALID.POLICE, INVALID.FBI, INVALID.CIA, INVALID.PLAYBOY, INVALID.MURDER, INVALID.HOMICIDE,
                    INVALID.SUICIDE, INVALID.VIRGIN, INVALID.MUSLIM, INVALID.VOILA};
    String[] VIETNAMS = new String[]{VIETNAM.VN};
    String[] LSPS = new String[]{LSP.TRANSLATION, LSP.LOCALIZE, LSP.LOCALIZATION, LSP.TRANSLATE, LSP.TRANSLATOR};
    String[] GOVS = new String[]{GOV.GOV};
    String[] ORGS = new String[]{ORG.ORG};
    String[] EDUS = new String[]{EDU.EDU, EDU.EDUCATION, EDU.SCHOOL, EDU.UNIVERSITY, EDU.COLLEGE, EDU.STUDENT, EDU.VIN_SCHOOL, EDU.LEARN,
                    EDU.STUDY, EDU.OXFORD, EDU.CAMBRIDGE, EDU.HARVARD, EDU.YALE, EDU.ALUMNI, EDU.TEACH, EDU.TRAINING, EDU.DAYCARE, EDU.KINDERGARTEN};
    String[] SALTLUXS = new String[]{SALTLUX.SALTLUX};

    String[] BUSINESS_GLOBALS = new String[]{BUSINESS_GLOBAL.GLOBAL, BUSINESS_GLOBAL.UNIVERSAL, BUSINESS_GLOBAL.GLOBE, BUSINESS_GLOBAL.WORLDWIDE,
            BUSINESS_GLOBAL.WORLD, BUSINESS_GLOBAL.INTERNATIONAL, BUSINESS_GLOBAL.MULTINATIONAL, BUSINESS_GLOBAL.NATIONWIDE, BUSINESS_GLOBAL.COCA_COLA,
            BUSINESS_GLOBAL.DHL, BUSINESS_GLOBAL.KINDLE, BUSINESS_GLOBAL.LEGO, BUSINESS_GLOBAL.BOOKING, BUSINESS_GLOBAL.PG, BUSINESS_GLOBAL.NIKON,
            BUSINESS_GLOBAL.CANON, BUSINESS_GLOBAL.LG, BUSINESS_GLOBAL.SHARP, BUSINESS_GLOBAL.STARBUCKS, BUSINESS_GLOBAL.PEPSI, BUSINESS_GLOBAL.NFL,
            BUSINESS_GLOBAL.LOGISTICS, BUSINESS_GLOBAL.FUJITSU, BUSINESS_GLOBAL.AMAZON, BUSINESS_GLOBAL.ALIBABA, BUSINESS_GLOBAL.FEDEX,
            BUSINESS_GLOBAL.EPSON, BUSINESS_GLOBAL.FUJIFILM, BUSINESS_GLOBAL.NESTLE, BUSINESS_GLOBAL.DECATHLON,
            BUSINESS_GLOBAL.GLOBAL, BUSINESS_GLOBAL.PHILIPS};
    String[] BUSINESS_MEDIAS = new String[]{BUSINESS_MEDIA.FOX, BUSINESS_MEDIA.CBS, BUSINESS_MEDIA.BBC, BUSINESS_MEDIA.CNN, BUSINESS_MEDIA.IMDB,
            BUSINESS_MEDIA.MEDIA, BUSINESS_MEDIA.MAGAZINE};
    String[] BUSINESS_AUTOMOTIVES = new String[]{BUSINESS_AUTOMOTIVE.TOYOTA, BUSINESS_AUTOMOTIVE.HONDA, BUSINESS_AUTOMOTIVE.HYUNDAI, BUSINESS_AUTOMOTIVE.GMC,
            BUSINESS_AUTOMOTIVE.FORD, BUSINESS_AUTOMOTIVE.LANDROVER, BUSINESS_AUTOMOTIVE.LEXUS, BUSINESS_AUTOMOTIVE.DUNLOP, BUSINESS_AUTOMOTIVE.FERRARI,
            BUSINESS_AUTOMOTIVE.LAMBORGHINI, BUSINESS_AUTOMOTIVE.MASERATI, BUSINESS_AUTOMOTIVE.FIAT, BUSINESS_AUTOMOTIVE.SCHAEFFLER, BUSINESS_AUTOMOTIVE.AIRBUS,
            BUSINESS_AUTOMOTIVE.BOEING_COM, BUSINESS_AUTOMOTIVE.SAMSUNG, BUSINESS_AUTOMOTIVE.SONY, BUSINESS_AUTOMOTIVE.TOSHIBA, BUSINESS_AUTOMOTIVE.BOSCH,
            BUSINESS_AUTOMOTIVE.HITACHI, BUSINESS_AUTOMOTIVE.VOLKSWAGEN, BUSINESS_AUTOMOTIVE.NISSAN, BUSINESS_AUTOMOTIVE.BROTHER, BUSINESS_AUTOMOTIVE.VINFAST,
            BUSINESS_AUTOMOTIVE.PANASONIC, BUSINESS_AUTOMOTIVE.BUGATTI, BUSINESS_AUTOMOTIVE.BMW, BUSINESS_AUTOMOTIVE.BENTLEY, BUSINESS_AUTOMOTIVE.AUDI,
            BUSINESS_AUTOMOTIVE.AAA, BUSINESS_AUTOMOTIVE.ABB, BUSINESS_AUTOMOTIVE.BRIDGE_STONE, BUSINESS_AUTOMOTIVE.ELECTRONIC, BUSINESS_AUTOMOTIVE.TESLA,
            BUSINESS_AUTOMOTIVE.CERN, BUSINESS_AUTOMOTIVE.ELECTROLUX, BUSINESS_AUTOMOTIVE.TRANSPORT};
    String[] BUSINESS_HOTELS = new String[]{BUSINESS_HOTEL.HOTEL, BUSINESS_HOTEL.ACCOMMODATION, BUSINESS_HOTEL.MARRIOTT, BUSINESS_HOTEL.SOFITEL, BUSINESS_HOTEL.SHANGRILA};
    String[] BUSINESS_FASHIONS = new String[]{BUSINESS_FASHION.JEWELRY, BUSINESS_FASHION.JEWEL, BUSINESS_FASHION.GUCCI, BUSINESS_FASHION.NIKE, BUSINESS_FASHION.HERMES,
            BUSINESS_FASHION.MANGO, BUSINESS_FASHION.ZARA, BUSINESS_FASHION.LANCOME, BUSINESS_FASHION.DIOR, BUSINESS_FASHION.CALVINKLEIN,
            BUSINESS_FASHION.PANDORA, BUSINESS_FASHION.ADIDAS};
    String[] BUSINESS_BANKINGS = new String[]{BUSINESS_BANKING.BANK, BUSINESS_BANKING.FINANCIAL, BUSINESS_BANKING.CREDIT, BUSINESS_BANKING.CITI_GROUP, BUSINESS_BANKING.CITI_BANK,
            BUSINESS_BANKING.WESTERNUNION, BUSINESS_BANKING.WORLD_BANK, BUSINESS_BANKING.DELOITTE, BUSINESS_BANKING.FOREX, BUSINESS_BANKING.HSBC, BUSINESS_BANKING.BITCOIN,
            BUSINESS_BANKING.PWC, BUSINESS_BANKING.KPMG, BUSINESS_BANKING.VISA, BUSINESS_BANKING.MASTERCARD, BUSINESS_BANKING.HDFC, BUSINESS_BANKING.CBA, BUSINESS_BANKING.PRUDENTIAL,
            BUSINESS_BANKING.BCG, BUSINESS_BANKING.BBT, BUSINESS_BANKING.ALLSTATE, BUSINESS_BANKING.ALLY, BUSINESS_BANKING.AMERICAN_EXPRESS, BUSINESS_BANKING.ANZ, BUSINESS_BANKING.AXA,
            BUSINESS_BANKING.BLOOM_BERG, BUSINESS_BANKING.INSURANCE};
    String[] BUSINESS_TECHS = new String[]{BUSINESS_TECH.TECH, BUSINESS_TECH.TECHNOLOGIES, BUSINESS_TECH.COMPUTER, BUSINESS_TECH.TECHNICAL, BUSINESS_TECH.SOFTWARE,
            BUSINESS_TECH.TECHNOLOGY, BUSINESS_TECH.ATT, BUSINESS_TECH.DELL, BUSINESS_TECH.IBM, BUSINESS_TECH.HKT, BUSINESS_TECH.INTEL, BUSINESS_TECH.NOKIA,
            BUSINESS_TECH.RMIT, BUSINESS_TECH.UNICOM, BUSINESS_TECH.PCCW, BUSINESS_TECH.AWS, BUSINESS_TECH.BAIDU, BUSINESS_TECH.ACCENTURE, BUSINESS_TECH.ANDROID,
            BUSINESS_TECH.CSC, BUSINESS_TECH.COMCAST, BUSINESS_TECH.TELECOM, BUSINESS_TECH.SIEMENS, BUSINESS_TECH.MOTOROLA, BUSINESS_TECH.HUAWEI, BUSINESS_TECH.HP_COM,
            BUSINESS_TECH.XIAOMI, BUSINESS_TECH.ADOBE, BUSINESS_TECH.CISCO};
    String[] BUSINESS_LAWS = new String[]{BUSINESS_LAW.LAW, BUSINESS_LAW.LEGAL, BUSINESS_LAW.CONSULTING};
    String[] BUSINESS_HEALTH_CARES = new String[]{BUSINESS_HEALTH_CARE.HEALTH, BUSINESS_HEALTH_CARE.HEALTH, BUSINESS_HEALTH_CARE.PHARMA, BUSINESS_HEALTH_CARE.SURGERY,
            BUSINESS_HEALTH_CARE.CLINIC, BUSINESS_HEALTH_CARE.HOSPITAL, BUSINESS_HEALTH_CARE.PFIZER, BUSINESS_HEALTH_CARE.ABBOTT, BUSINESS_HEALTH_CARE.AETNA,
            BUSINESS_HEALTH_CARE.BMS, BUSINESS_HEALTH_CARE.ASTRAZENECA};

    interface YAHOO_MAIL {
        String YAHOO = "@yahoo.";
        String YMAIL = "@ymail.";
        String YHAOO = "@yhaoo.";
        String YAHOOMAIL = "@yahoomail.com";
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
        String WINDOWSLIVE = "@windowslive.";
        String OUTLOOK = "@outlook.";
        String LIVE = "@live.";
        String MSN = "@msn.";
        String GO = "@go.com";
        String AOL = "@aol.";
        String INBOX = "@inbox.";
        String EMAIL = "@email.";
        String MAIL = "@mail.";
        String HOTMAIL = "@hotmail.";
        String QQ = "@qq.";
        String REDIFFMAIL = "@rediffmail.";
        String Q = "@q.com";
        String GMX = "@gmx.com";
        String ZOHO = "@zoho.";
        String YANDEX = "@yandex.";
        String MYSPACE = "@myspace.";
        String COX = "@cox.net";
        String _126 = "@126.com";
        String HANMAIL = "@hanmail.";
        String DOMAIN = "@domain.";
        String EARTHLINK = "@earthlink.";
        String _139 = "@139.com";
        String _163 = "@163.com";
        String VERIZON = "@verizon.net";
        String FREEMAIL = "@freemail.";
        String MYNET = "@mynet.";
        String SBCGLOBAL = "@sbcglobal.net";
        String NAVER = "@naver.com";
        String DAUM = "@daum.";
        String AIM = "@aim.com";
        String VODAFONE = "@vodafone.com";
        String JUNO = "@juno.com";
        String CHARTER = "@charter.net";
        String SPECTRUM = "@spectrum.net";
        String MYWAY = "@myway.com";
        String CS = "@cs.com";
        String _012 = "@012.net.il";
        String HOTMAILL = "@hotmaill.";
        String FREE = "@free.fr";
        String EMAOIL = "@emaoil.com";
        String COCMAST = "@cocmast.net";
        String OTMLAIL = "@otmlail.com";
        String TOM = "@tom.com";
        String LINKEDIN = "@linkedin.com";
        String _1298 = "@1298.org.in";
        String NTLWORLD = "@ntlworld.com";
        String INYOURWORLD = "@inyourworld.com";
        String HOTMAL = "@hotmal.com";
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
        String NAVY_MIL = "navy.mil";
        String USCG_MIL = "uscg.mil";
        String US_AF_MIL = "us.af.mil";
        String USMC_MIL = "usmc.mil";
        String POLICE = "police.";
        String FBI = "fbi.";
        String CIA = "@cia.gov";
        String PLAYBOY = "playboy";
        String MURDER = "murder";
        String HOMICIDE = "homicide";
        String SUICIDE = "suicide";
        String VIRGIN = "virgin";
        String MUSLIM = "muslim";
        String VOILA = "@voila.fr";
    }

    interface VIETNAM {
        String VN = ".vn";
    }

    interface LSP {
        String TRANSLATION = "translation";
        String LOCALIZE = "localize";
        String LOCALIZATION = "localization";
        String TRANSLATE = "translate";
        String TRANSLATOR = "translator";
//        String LSP = "lsp";
    }

    interface GOV {
        String GOV = ".gov";
//        String NATION = "national";
    }

    interface ORG {
        String ORG = ".org";
    }

    interface EDU {
        String EDU = ".edu";
        String EDUCATION = "education";
        String SCHOOL = "school";
        String UNIVERSITY = "university";
        String COLLEGE = "college";
        String STUDENT = "student";
        String VIN_SCHOOL = "vinschool";
        String LEARN = "learn";
        String STUDY  = "study ";
        String OXFORD = "oxford";
        String CAMBRIDGE = "cambridge";
        String HARVARD = "harvard";
        String YALE = "yale";
        String ALUMNI = "alumni";
        String TEACH = "teach";
        String TRAINING = "training";
        String DAYCARE = "daycare";
        String KINDERGARTEN = "kindergarten";
    }

    interface SALTLUX {
        String SALTLUX = "@saltlux.com";
    }

    interface BUSINESS_GLOBAL {
        String GLOBAL = "global";
        String UNIVERSAL = "universal";
        String GLOBE = "globe";
        String WORLDWIDE = "worldwide";
        String WORLD = "world";
        String INTERNATIONAL = "international";
        String MULTINATIONAL = "multinational";
        String NATIONWIDE = "nationwide";
        String COCA_COLA = "coca-cola";
        String DHL = "dhl.com";
        String KINDLE = "kindle.com";
        String LEGO = "lego.com";
        String BOOKING = "booking.com";
        String NIKON = "nikon.";
        String CANON = "canon.";
        String SHARP = "@sharp.com";
        String NFL = "nfl.com";
        String FUJITSU = "fujitsu";
        String AMAZON = "amazon";
        String ALIBABA = "alibaba";
        String FEDEX = "fedex.com";
        String EPSON = "epson.";
        String FUJIFILM = "fujifilm";
        String NESTLE = "nestle.com";
        String DECATHLON = "decathlon";
        String LG = "@lg.com";
        String PG = "@pg.com";
        String STARBUCKS = "@starbucks.com";
        String PHILIPS = "@philips.com";
        String PEPSI = "@pepsi.com";
        String LOGISTICS = "logistics";
    }

    interface BUSINESS_MEDIA {
        String FOX = "fox";
        String CBS = "cbs";
        String BBC = "bbc";
        String CNN = "cnn";
        String IMDB = "imdb";
        String MEDIA = "media";
        String MAGAZINE = "magazine";
    }

    interface BUSINESS_AUTOMOTIVE {
        String TOYOTA = "toyota";
        String HONDA = "honda";
        String HYUNDAI = "hyundai";
        String GMC = "GMC";
        String FORD = "ford";
        String LANDROVER = "landrover";
        String LEXUS = "lexus";
        String DUNLOP = "dunlop";
        String FERRARI = "ferrari";
        String LAMBORGHINI = "lamborghini";
        String MASERATI = "maserati";
        String FIAT = "fiat";
        String SCHAEFFLER = "schaeffler";
        String AIRBUS = "airbus";
        String BOEING_COM = "boeing.com";
        String SAMSUNG = "samsung";
        String SONY = "sony";
        String TOSHIBA = "toshiba";
        String BOSCH = "bosch";
        String HITACHI = "hitachi";
        String VOLKSWAGEN = "volkswagen";
        String NISSAN = "nissan";
        String BROTHER = "brother";
        String VINFAST = "vinfast";
        String PANASONIC = "panasonic";
        String BUGATTI = "bugatti";
        String BMW = "bmw";
        String BENTLEY = "bentley";
        String AUDI = "audi";
        String AAA = "aaa";
        String ABB = "abb";
        String BRIDGE_STONE = "bridgestone";
        String ELECTRONIC = "electronic";
        String TESLA = "tesla";
        String CERN = "cern";
        String ELECTROLUX = "electrolux";
        String TRANSPORT = "transport";
    }

    interface BUSINESS_HOTEL {
        String HOTEL = "hotel";
        String ACCOMMODATION = "accommodation";
        String MARRIOTT = "marriott";
        String SOFITEL = "sofitel";
        String SHANGRILA = "shangrila";
    }

    interface BUSINESS_FASHION {
        String JEWELRY = "jewelry";
        String JEWEL = "jewel";
        String GUCCI = "gucci";
        String NIKE = "nike";
        String HERMES = "hermes";
        String MANGO = "mango";
        String ZARA = "zara";
        String LANCOME = "lancome";
        String DIOR = "dior";
        String CALVINKLEIN = "calvinklein";
        String PANDORA = "pandora";
        String ADIDAS = "adidas";
    }

    interface BUSINESS_TECH {
        String TECH = "tech";
        String TECHNOLOGIES = "technologies";
        String COMPUTER = "computer";
        String TECHNICAL = "technical";
        String SOFTWARE = "software";
        String TECHNOLOGY = "technology";
        String ATT = "@att.";
        String DELL = "dell";
        String IBM = "ibm";
        String HKT = "hkt";
        String INTEL = "intel";
        String NOKIA = "nokia";
        String RMIT = "rmit";
        String UNICOM = "unicom";
        String PCCW = "pccw";
        String AWS = "aws";
        String BAIDU = "baidu";
        String ACCENTURE = "accenture";
        String ANDROID = "android";
        String CSC = "csc";
        String COMCAST = "comcast";
        String TELECOM = "telecom";
        String SIEMENS = "siemens";
        String MOTOROLA = "motorola";
        String HUAWEI = "huawei";
        String HP_COM = "@hp.com";
        String XIAOMI = "xiaomi";
        String ADOBE = "adobe";
        String CISCO = "@cisco";
    }

    interface BUSINESS_BANKING {
        String BANK = "bank";
        String FINANCIAL = "financial";
        String CREDIT = "credit";
        String CITI_GROUP = "citigroup";
        String CITI_BANK = "citibank";
        String WESTERNUNION = "westernunion";
        String WORLD_BANK = "worldbank";
        String DELOITTE = "deloitte";
        String FOREX = "forex";
        String HSBC = "hsbc";
        String BITCOIN = "bitcoin";
        String PWC = "pwc";
        String KPMG = "kpmg";
        String VISA = "visa";
        String MASTERCARD = "mastercard";
        String HDFC = "hdfc";
        String CBA = "cba";
        String PRUDENTIAL = "prudential";
        String BCG = "bcg";
        String BBT = "bbt";
        String ALLSTATE = "allstate";
        String ALLY = "ally";
        String AMERICAN_EXPRESS = "americanexpress";
        String ANZ = "anz";
        String AXA = "axa";
        String BLOOM_BERG = "bloomberg";
        String INSURANCE = "insurance";
    }

    interface BUSINESS_LAW {
        String LAW = "law";
        String LEGAL = "legal";
        String CONSULTING = "consulting";
    }

    interface BUSINESS_HEALTH_CARE {
        String HEALTH = "health";
        String PHARMA = "pharma";
        String SURGERY = "surgery";
        String CLINIC = "clinic";
        String HOSPITAL = "hospital";
        String PFIZER = "pfizer";
        String ABBOTT = "abbott";
        String AETNA = "aetna";
        String BMS = "bms";
        String ASTRAZENECA = "astrazeneca";
    }
}
