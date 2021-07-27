package com.shjang.portfolio.mall.web.dto;

import com.shjang.portfolio.mall.domain.art.Art;
import com.shjang.portfolio.mall.domain.art.ArtImage;
import lombok.Getter;

//작품 1개를 받을 때 사용
@Getter
public class ArtResponseDto {

    private Long id;
    private String title;
    private String artist;
    private String description;
    private int price;
    private ArtImage artImage;
    private Long userId;
    private int categoryId;

    public ArtResponseDto(Art entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.artist = entity.getArtist();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.artImage = entity.getArtImage();
        this.userId = entity.getUserId();
        this.categoryId = entity.getCategoryId();
    }

}
