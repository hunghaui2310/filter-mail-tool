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
@Table(name = "tbl_others_email")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OtherModel{

    @Id
    @Column(name = "MEMBER_ID")
    private String memberId;

    @Column(name = "MEMBER_PRIMARY_EMAIL")
    private String memberPrimaryEmail;
}
