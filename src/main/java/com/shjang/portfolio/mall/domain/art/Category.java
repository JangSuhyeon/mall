package com.shjang.portfolio.mall.domain.art;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Entity
public class Category {
    @Id
    private int id;

    @Column(nullable = true)
    private Integer superId;

    private String name;

}
