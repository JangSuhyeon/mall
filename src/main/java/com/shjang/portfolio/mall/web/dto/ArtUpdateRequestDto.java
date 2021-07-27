package com.shjang.portfolio.mall.web.dto;

import com.shjang.portfolio.mall.domain.art.ArtImage;
import com.shjang.portfolio.mall.domain.art.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//작품을 수정할 때 사용
@Getter
@NoArgsConstructor
public class ArtUpdateRequestDto {

    private String title;
    private String artist;
    private String description;
    private int price;
    private Long artImageId;
    private int categoryId;

    @Builder
    public ArtUpdateRequestDto(String title, String artist, String description, int price, Long artImageId, int categoryId) {
        this.title = title;
        this.artist = artist;
        this.description = description;
        this.price = price;
        this.artImageId = artImageId;
    }

}
