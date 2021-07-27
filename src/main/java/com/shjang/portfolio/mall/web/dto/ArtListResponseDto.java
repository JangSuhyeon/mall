package com.shjang.portfolio.mall.web.dto;

import com.shjang.portfolio.mall.domain.art.Art;
import com.shjang.portfolio.mall.domain.art.ArtImage;
import lombok.Getter;

import java.time.LocalDateTime;

//작품을 list로 받을 때 사용
@Getter
public class ArtListResponseDto {
    private Long id;
    private String title;
    private String artist;
    private String description;
    private int price;
    private ArtImage artImage;
    private LocalDateTime modifiedDate;

    public ArtListResponseDto(Art entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.artist = entity.getArtist();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.artImage = entity.getArtImage();
        this.modifiedDate = entity.getModifiedDate();
    }

}
