package com.saltlux.tool.filter.tool.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "idemail")
public class IdEmail {

    @Id
    @Column(name = "MEMBER_ID")
    private String memberId;

    @Column(name = "MEMBER_PRIMARY_EMAIL")
    private String memberPrimaryEmail;

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberPrimaryEmail() {
        return memberPrimaryEmail;
    }

    public void setMemberPrimaryEmail(String memberPrimaryEmail) {
        this.memberPrimaryEmail = memberPrimaryEmail;
    }
}
