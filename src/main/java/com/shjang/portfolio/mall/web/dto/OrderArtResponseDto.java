package com.shjang.portfolio.mall.web.dto;

import com.shjang.portfolio.mall.domain.art.Art;
import com.shjang.portfolio.mall.domain.art.ArtImage;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class OrderArtResponseDto {

    private Long id;
    private String title;
    private int price;
    private ArtImage artImage;
    private LocalDate orderDate;

    @Builder
    public OrderArtResponseDto(Long id, String title, int price, ArtImage artImage, LocalDate orderDate) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.artImage = artImage;
        this.orderDate = orderDate;
    }
}
