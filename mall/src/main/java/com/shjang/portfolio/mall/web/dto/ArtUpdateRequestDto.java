package com.shjang.portfolio.mall.web.dto;

import com.shjang.portfolio.mall.domain.art.ArtImage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//작품을 수정할 때 사용
@Getter
@NoArgsConstructor
public class ArtUpdateRequestDto {

    private Long id;
    private String title;
    private String artist;
    private String description;
    private int price;

    @Builder
    public ArtUpdateRequestDto(Long id, String title, String artist, String description, int price) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.description = description;
        this.price = price;
    }

}
