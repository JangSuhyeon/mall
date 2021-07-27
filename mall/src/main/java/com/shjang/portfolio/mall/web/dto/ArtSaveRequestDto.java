package com.shjang.portfolio.mall.web.dto;

import com.shjang.portfolio.mall.domain.art.Art;
import com.shjang.portfolio.mall.domain.art.ArtImage;
import com.shjang.portfolio.mall.domain.user.User;
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
    private String userEmail;
    private ArtImage artImage;

    @Builder
    public ArtSaveRequestDto(String title, String artist, String description, int price, String userEmail,ArtImage artImage) {
        this.title = title;
        this.artist = artist;
        this.description = description;
        this.price = price;
        this.userEmail = userEmail;
        this.artImage = artImage;
    }

    public Art toEntity() {
        return Art.builder()
                .title(title)
                .artist(artist)
                .description(description)
                .price(price)
                .userEmail(userEmail)
                .artImage(artImage)
                .build();
    }

}
