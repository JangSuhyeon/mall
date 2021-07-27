package com.shjang.portfolio.mall.domain.art;

import com.shjang.portfolio.mall.domain.BaseTimeEntity;
import com.shjang.portfolio.mall.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

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

    private String userEmail;

    @OneToOne
    @JoinColumn(name = "id")
    private ArtImage artImage; //ArtImage 테이블과 조인


    @Builder
    public Art(String title, String artist, String description, int price, String userEmail,ArtImage artImage) {
        this.title = title;
        this.artist = artist;
        this.description = description;
        this.price = price;
        this.userEmail = userEmail;
        this.artImage = artImage;
    }

    public void update(String title, String artist, String description, int price, ArtImage artImage){
        this.title = title;
        this.artist = artist;
        this.description = description;
        this.price = price;
        this.artImage = artImage;
    }

}
