package com.saltlux.tool.filter.tool.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StatisticDto implements Serializable {

    private long appleMail;
    private long gmail;
    private long businessMail;
    private long govMail;
    private long invalidMail;
    private long lspMail;
    private long otherMail;
    private long roleMail;
    private long vietnamMail;
    private long yahooMail;

}
