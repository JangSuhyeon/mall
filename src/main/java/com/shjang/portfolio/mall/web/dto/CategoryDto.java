package com.shjang.portfolio.mall.web.dto;

import com.shjang.portfolio.mall.domain.art.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryDto {

    private int id;
    private Integer superId;
    private String name;

    @Builder
    public CategoryDto(Category entity) {
        this.id = entity.getId();
        this.superId = entity.getSuperId();
        this.name = entity.getName();
    }
}
