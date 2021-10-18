SELECT COUNT(*) from tbl_business_email tbe;
SELECT COUNT(*) from tbl_others_email toe;
SELECT COUNT(*) FROM tbl_apple_email tae;
SELECT COUNT(*) FROM tbl_gmail tg;
SELECT COUNT(*) FROM tbl_gov_email tge;
SELECT COUNT(*) FROM tbl_invalid_email tie;
SELECT COUNT(*) FROM tbl_role_email tre;
SELECT COUNT(*) FROM tbl_yahoo_mail tym WHERE MEMBER_PRIMARY_EMAIL like '%@gmail.com%';
SELECT COUNT(*) FROM idemail i;

SELECT * from linked_in.idemail i limit 2000;
SELECT * from linked_in.tbl_gov_email tge limit 2000;


SELECT * FROM tbl_business_email tbe WHERE MEMBER_PRIMARY_EMAIL like '%@microsoft.com%' 
or MEMBER_PRIMARY_EMAIL LIKE '%@windowslive.%' or MEMBER_PRIMARY_EMAIL LIKE '%@outlook.%' or MEMBER_PRIMARY_EMAIL LIKE '%@live.%'
or MEMBER_PRIMARY_EMAIL LIKE '%@msn.%' 		   or MEMBER_PRIMARY_EMAIL LIKE '%@go.com%'   or MEMBER_PRIMARY_EMAIL LIKE '%@aol.%'
or MEMBER_PRIMARY_EMAIL LIKE '%@hotmail.%' 	   or MEMBER_PRIMARY_EMAIL LIKE '%@qq.%' 	  or MEMBER_PRIMARY_EMAIL LIKE '%@rediffmail.%'
or MEMBER_PRIMARY_EMAIL LIKE '%@q.com%' 	   or MEMBER_PRIMARY_EMAIL LIKE '%@gmx.com%'  or MEMBER_PRIMARY_EMAIL LIKE '%@zoho%'
or MEMBER_PRIMARY_EMAIL LIKE '%@yandex.%' 	   or MEMBER_PRIMARY_EMAIL LIKE '%@myspace%'
or MEMBER_PRIMARY_EMAIL LIKE '%@inbox.%' 	   or MEMBER_PRIMARY_EMAIL LIKE '%@email.' 	  or MEMBER_PRIMARY_EMAIL LIKE '%@mail.com%';

SELECT * FROM tbl_business_email tbe WHERE MEMBER_PRIMARY_EMAIL like 'info@%' 
or MEMBER_PRIMARY_EMAIL LIKE 'admin@%' or MEMBER_PRIMARY_EMAIL LIKE 'administrator@%' or MEMBER_PRIMARY_EMAIL LIKE 'administration@%'
or MEMBER_PRIMARY_EMAIL LIKE 'marketing@%' 		   or MEMBER_PRIMARY_EMAIL LIKE 'accounts@%'   or MEMBER_PRIMARY_EMAIL LIKE 'account@%'
or MEMBER_PRIMARY_EMAIL LIKE 'email@%' 		   or MEMBER_PRIMARY_EMAIL LIKE 'inbox@%'   or MEMBER_PRIMARY_EMAIL LIKE 'support@%' or MEMBER_PRIMARY_EMAIL LIKE 'news@%'
or MEMBER_PRIMARY_EMAIL LIKE 'hr@%' 	   or MEMBER_PRIMARY_EMAIL LIKE 'manager@%' 	  or MEMBER_PRIMARY_EMAIL LIKE 'mail@%';

SELECT * FROM tbl_business_email tbe WHERE MEMBER_PRIMARY_EMAIL like '%porn%' 
or MEMBER_PRIMARY_EMAIL LIKE '%sex%' or MEMBER_PRIMARY_EMAIL LIKE '%navy.mil%' or MEMBER_PRIMARY_EMAIL LIKE '%us.af.mil%'  or MEMBER_PRIMARY_EMAIL LIKE '%uscg.mil%'
or MEMBER_PRIMARY_EMAIL LIKE '%usmc.mil%' 		   or MEMBER_PRIMARY_EMAIL LIKE '%police.%' or MEMBER_PRIMARY_EMAIL LIKE '%fbi.%' or MEMBER_PRIMARY_EMAIL LIKE '%@cia.gov%';

INSERT INTO tbl_invalid_email (MEMBER_ID, MEMBER_PRIMARY_EMAIL) values ('14999338', 'sexy.marketing@gmail.com');
DELETE FROM tbl_business_email WHERE MEMBER_ID = '14999338';

SELECT * FROM tbl_business_email tbe WHERE MEMBER_PRIMARY_EMAIL like '%.vn%';

SELECT * FROM tbl_business_email tbe WHERE MEMBER_PRIMARY_EMAIL like '%translation%' 
or MEMBER_PRIMARY_EMAIL LIKE '%localize%' or MEMBER_PRIMARY_EMAIL LIKE '%localization%' or MEMBER_PRIMARY_EMAIL LIKE '%translate%';

SELECT * FROM tbl_business_email tbe WHERE MEMBER_PRIMARY_EMAIL like '%.gov%';

SELECT * from tbl_business_email tbe WHERE MEMBER_ID = '46207061';

SELECT * FROM tbl_role_email tre WHERE MEMBER_PRIMARY_EMAIL like '%mail@%';

SELECT * FROM tbl_business_email tbe WHERE MEMBER_PRIMARY_EMAIL like 'email@%';
SELECT * FROM tbl_business_email tbe WHERE MEMBER_PRIMARY_EMAIL like 'hr@%';
SELECT COUNT(*) FROM tbl_business_email tbe WHERE MEMBER_PRIMARY_EMAIL like 'email@%';


CREATE TABLE `tbl_linkedin_account` (
  `id` varchar(255) NOT NULL DEFAULT '',
  `full_name` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `middle_initial` varchar(255) DEFAULT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `birth_year` varchar(255) DEFAULT NULL,
  `birth_date` varchar(255) DEFAULT NULL,
  `linkedin_url` varchar(255) DEFAULT NULL,
  `linkedin_username` varchar(255) DEFAULT NULL,
  `linkedin_id` varchar(255) DEFAULT NULL,
  `facebook_url` varchar(255) DEFAULT NULL,
  `facebook_username` varchar(255) DEFAULT NULL,
  `facebook_id` varchar(255) DEFAULT NULL,
  `twitter_url` varchar(255) DEFAULT NULL,
  `twitter_username` varchar(255) DEFAULT NULL,
  `github_url` varchar(255) DEFAULT NULL,
  `github_username` varchar(255) DEFAULT NULL,
  `work_email` varchar(255) DEFAULT NULL,
  `mobile_phone` varchar(255) DEFAULT NULL,
  `industry` varchar(255) DEFAULT NULL,
  `job_title` varchar(255) DEFAULT NULL,
  `job_title_role` varchar(255) DEFAULT NULL,
  `job_title_sub_role` varchar(255) DEFAULT NULL,
  `job_title_levels` varchar(255) DEFAULT NULL,
  `job_company_id` varchar(255) DEFAULT NULL,
  `job_company_name` varchar(255) DEFAULT NULL,
  `job_company_website` varchar(255) DEFAULT NULL,
  `job_company_size` varchar(255) DEFAULT NULL,
  `job_company_founded` varchar(255) DEFAULT NULL,
  `job_company_industry` varchar(255) DEFAULT NULL,
  `job_company_linkedin_url` varchar(255) DEFAULT NULL,
  `job_company_linkedin_id` varchar(255) DEFAULT NULL,
  `job_company_facebook_url` varchar(255) DEFAULT NULL,
  `job_company_twitter_url` varchar(255) DEFAULT NULL,
  `job_company_location_name` varchar(255) DEFAULT NULL,
  `job_company_location_locality` varchar(255) DEFAULT NULL,
  `job_company_location_metro` varchar(255) DEFAULT NULL,
  `job_company_location_region` varchar(255) DEFAULT NULL,
  `job_company_location_geo` varchar(255) DEFAULT NULL,
  `job_company_location_street_address` varchar(255) DEFAULT NULL,
  `job_company_location_address_line_2` varchar(255) DEFAULT NULL,
  `job_company_location_postal_code` varchar(255) DEFAULT NULL,
  `job_company_location_country` varchar(255) DEFAULT NULL,
  `job_company_location_continent` varchar(255) DEFAULT NULL,
  `job_last_updated` varchar(255) DEFAULT NULL,
  `job_start_date` varchar(255) DEFAULT NULL,
  `job_summary` varchar(255) DEFAULT NULL,
  `location_name` varchar(255) DEFAULT NULL,
  `location_locality` varchar(255) DEFAULT NULL,
  `location_metro` varchar(255) DEFAULT NULL,
  `location_region` varchar(255) DEFAULT NULL,
  `location_country` varchar(255) DEFAULT NULL,
  `location_continent` varchar(255) DEFAULT NULL,
  `location_street_address` varchar(255) DEFAULT NULL,
  `location_address_line_2` varchar(255) DEFAULT NULL,
  `location_postal_code` varchar(255) DEFAULT NULL,
  `location_geo` varchar(255) DEFAULT NULL,
  `location_last_updated` varchar(255) DEFAULT NULL,
  `linkedin_connections` varchar(255) DEFAULT NULL,
  `inferred_salary` varchar(255) DEFAULT NULL,
  `inferred_years_experience` varchar(255) DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL,
  `phone_numbers` varchar(255) DEFAULT NULL,
  `emails` varchar(255) DEFAULT NULL,
  `interests` varchar(255) DEFAULT NULL,
  `skills` varchar(1000) DEFAULT NULL,
  `location_names` varchar(255) DEFAULT NULL,
  `regions` varchar(255) DEFAULT NULL,
  `countries` varchar(255) DEFAULT NULL,
  `street_addresses` varchar(255) DEFAULT NULL,
  `experience` varchar(5000) DEFAULT NULL,
  `education` varchar(2000) DEFAULT NULL,
  `profiles` varchar(255) DEFAULT NULL,
  `certifications` varchar(255) DEFAULT NULL,
  `languages` varchar(255) DEFAULT NULL,
  `version_status` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

ALTER TABLE tbl_linkedin_account CHANGE profiles profiles VARCHAR(2000) CHARACTER SET utf8 COLLATE utf8_general_ci;
ALTER TABLE tbl_linkedin_account CHANGE skills skills VARCHAR(1000) CHARACTER SET utf8 COLLATE utf8_general_ci;
ALTER TABLE tbl_linkedin_account CHANGE summary summary VARCHAR(5000) CHARACTER SET utf8 COLLATE utf8_general_ci;
ALTER TABLE tbl_linkedin_account CHANGE interests interests VARCHAR(4000) CHARACTER SET utf8 COLLATE utf8_general_ci;
ALTER TABLE tbl_linkedin_account CHANGE regions regions VARCHAR(3000) CHARACTER SET utf8 COLLATE utf8_general_ci;
ALTER TABLE tbl_linkedin_account CHANGE certifications certifications VARCHAR(3500) CHARACTER SET utf8 COLLATE utf8_general_ci;
ALTER TABLE tbl_linkedin_account CHANGE certifications certifications VARCHAR(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

SELECT COUNT(*) FROM linked_in.tbl_linkedin_account tl;

repair table idemail 

-- linked_in.tbl_business_email definition

CREATE TABLE `tbl_business_banking_mail` (
  `MEMBER_ID` varchar(255) NOT NULL DEFAULT '',
  `MEMBER_PRIMARY_EMAIL` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`MEMBER_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
