package com.shjang.portfolio.mall.domain.art;

import com.shjang.portfolio.mall.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//작품
@Getter
@NoArgsConstructor
@Entity
public class Art extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 500,nullable = false)
    private String title;

    @Column(length = 500,nullable = false)
    private String artist;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    private int price;

    @OneToOne
    @JoinColumn(name = "ARTIMAGE_ID")
    ArtImage artImage;

    private Long userId;
    private int categoryId;

    @Builder
    public Art(String title, String artist, String description, int price, Long userId, ArtImage artImage, int categoryId ) {
        this.title = title;
        this.artist = artist;
        this.description = description;
        this.price = price;
        this.userId = userId;
        this.artImage = artImage;
        this.categoryId = categoryId;
    }

    public void update(String title, String artist, String description, int price, int categoryId) {
        this.title = title;
        this.artist = artist;
        this.description = description;
        this.price = price;
        this.categoryId = categoryId;
    }

}
