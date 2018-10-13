package com.tcbs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioItem {

    @Id
    private String id;
    private String userId;
    private String product;
    private Double balance;
    private Double delta;
    private Date updatedDate;
}