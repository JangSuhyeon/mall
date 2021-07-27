package com.shjang.portfolio.mall.web.dto;

import com.shjang.portfolio.mall.domain.art.Art;
import com.shjang.portfolio.mall.domain.art.ArtImage;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

//작품을 저장할 때 사용
@Data
@NoArgsConstructor
public class ArtSaveRequestDto {

    private String title;
    private String artist;
    private String description;
    private int price;
    private ArtImage artImage;
    private Long userId;
    private int categoryId;

    @Builder
    public ArtSaveRequestDto(String title, String artist, String description, int price, ArtImage artImage, Long userId, int categoryId) {
        this.title = title;
        this.artist = artist;
        this.description = description;
        this.price = price;
        this.userId = userId;
        this.artImage = artImage;
        this.categoryId = categoryId;
    }

    public Art toEntity() {
        return Art.builder()
                .title(title)
                .artist(artist)
                .description(description)
                .price(price)
                .userId(userId)
                .artImage(artImage)
                .categoryId(categoryId)
                .build();
    }

}
