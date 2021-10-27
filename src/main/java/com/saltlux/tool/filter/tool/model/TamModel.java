package com.saltlux.tool.filter.tool.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_tam")
@NoArgsConstructor
@Getter
@Setter
public class TamModel extends BaseModel {
}
