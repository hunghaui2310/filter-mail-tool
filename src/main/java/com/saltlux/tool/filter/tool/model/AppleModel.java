package com.saltlux.tool.filter.tool.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.function.Function;

@Entity
@Table(name = "tbl_apple_mail")
@NoArgsConstructor
public class AppleModel extends BaseModel {
}
