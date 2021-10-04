package com.saltlux.tool.filter.tool.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_linkedin_account")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LinkedinAccountModel {

    @Id
    private String id;
//    private String fullName;
//    private String firstName;
//    private String middleInitial;
//    private String middleName;
//    private String lastName;
    private String gender;
//    private String birthYear;
//    private String birthDate;
//    private String linkedinUrl;
//    private String linkedinUsername;
//    private String linkedinId;
//    private String facebookUrl;
//    private String facebookUsername;
//    private String facebookId;
//    private String twitterUrl;
//    private String twitterUsername;
//    private String githubUrl;
//    private String githubUsername;
    private String workEmail;
//    private String mobilePhone;
    private String industry;
//    private String jobTitle;
//    private String jobTitleRole;
//    private String jobTitleSubRole;
//    private String jobTitleLevels;
//    private String jobCompanyId;
//    private String jobCompanyName;
//    private String jobCompanyWebsite;
//    private String jobCompanySize;
//    private String jobCompanyFounded;
//    private String jobCompanyIndustry;
//    private String jobCompanyLinkedinUrl;
//    private String jobCompanyLinkedinId;
//    private String jobCompanyFacebookUrl;
//    private String jobCompanyTwitterUrl;
//    private String jobCompanyLocationName;
//    private String jobCompanyLocationLocality;
//    private String jobCompanyLocationMetro;
//    private String jobCompanyLocationRegion;
//    private String jobCompanyLocationGeo;
//    private String jobCompanyLocationStreetAddress;
//    @Column(name = "job_company_location_address_line_2")
//    private String jobCompanyLocationAddressLine2;
//    private String jobCompanyLocationPostalCode;
//    private String jobCompanyLocationCountry;
//    private String jobCompanyLocationContinent;
//    private String jobLastUpdated;
//    private String jobStartDate;
//    private String jobSummary;
//    private String locationName;
//    private String locationLocality;
//    private String locationMetro;
//    private String locationRegion;
//    private String locationCountry;
//    private String locationContinent;
//    private String locationStreetAddress;
//    @Column(name = "location_address_line_2")
//    private String locationAddressLine2;
//    private String locationPostalCode;
//    private String locationGeo;
//    private String locationLastUpdated;
//    private String linkedinConnections;
//    private String inferredSalary;
//    private String inferredYearsExperience;
    private String summary;
//    private String phoneNumbers;
    private String emails;
    private String interests;
    private String skills;
    private String locationNames;
    private String regions;
    private String countries;
//    private String streetAddresses;
    private String experience;
    private String education;
    private String profiles;
    private String certifications;
    private String languages;
//    private String versionStatus;
}
