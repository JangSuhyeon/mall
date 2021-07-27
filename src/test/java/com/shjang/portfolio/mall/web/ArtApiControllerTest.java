package com.shjang.portfolio.mall.web;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shjang.portfolio.mall.domain.art.Art;
import com.shjang.portfolio.mall.domain.art.ArtRepository;
import com.shjang.portfolio.mall.web.dto.ArtSaveRequestDto;
import com.shjang.portfolio.mall.web.dto.ArtUpdateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ArtApiControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ArtRepository artRepository;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception {
        artRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles="USER")
    public void Art_등록된다() throws Exception {
        //given
        String title = "작품명";
        String artist = "작가";
        String description = "간단 설명입니다.";
        int price = 1000;

        ArtSaveRequestDto requestDto = ArtSaveRequestDto.builder()
                .title(title)
                .artist(artist)
                .description(description)
                .price(price)
                .build();

        String url = "http://localhost:" + port + "/api/v1/art";

        //when
        //ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        //assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        //assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Art> all = artRepository.findAll();
        System.out.println("등록 : " + all.get(0).getId());
        System.out.println("등록 : " + all.get(0).getTitle());
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getArtist()).isEqualTo(artist);
    }

    @Test
    @WithMockUser(roles="USER")
    public void Art_수정된다() throws Exception {
        //given
        Art savedPosts = artRepository.save(Art.builder()
                .title("title")
                .artist("artist")
                .description("description")
                .price(1234)
                .build());

        Long updateId = savedPosts.getId();
        String expectedTitle = "title2";
        String expectedContent = "content2";

        ArtUpdateRequestDto requestDto = ArtUpdateRequestDto.builder()
                .title(expectedTitle)
                .artist(expectedContent)
                .description("description")
                .price(1234)
                .build();

        System.out.println("수정전 : " + requestDto.getTitle());
        System.out.println("수정전 : " + updateId);

        String url = "http://localhost:" + port + "/api/v1/art/" + updateId;

        //when
        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Art> all = artRepository.findAll();
        System.out.println(all.get(1).getId());
        System.out.println(all.get(1).getTitle());
        assertThat(all.get(1).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(1).getArtist()).isEqualTo(expectedContent);
    }

}