package com.shjang.portfolio.mall.domain.art;

import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArtRepositoryTest {

    @Autowired
    ArtRepository artRepository;

    @After
    public void cleanup(){
        artRepository.deleteAll();
    }

    @Test
    public void 작품저장_불러오기(){
        //given
        String title = "작품명";
        String artist = "작가";
        String description = "간단 설명입니다.";
        int price = 1000;

        artRepository.save(Art.builder()
                .title(title)
                .artist(artist)
                .description(description)
                .price(price)
                .build());

        //when
        List<Art> artsList = artRepository.findAll();

        //then
        Art art = artsList.get(0);
        assertThat(art.getTitle()).isEqualTo(title);
        assertThat(art.getArtist()).isEqualTo(artist);

    }

    @Test
    public void BaseTimeEntity_등록 () {
        //given
        LocalDateTime now = LocalDateTime.of(2019,6,4,0,0,0);
        artRepository.save(Art.builder()
                .title("title")
                .artist("artist")
                .description("description")
                .price(1234)
                .build());

        //when
        List<Art> artList = artRepository.findAll();

        //then
        Art art = artList.get(0);

        System.out.println(">>>>>>>>>>>>>>>> createDate="+art.getCreatedDate()+", modifiedDate="+art.getModifiedDate());

        assertThat(art.getCreatedDate()).isAfter(now);
        assertThat(art.getModifiedDate()).isAfter(now);
    }

}