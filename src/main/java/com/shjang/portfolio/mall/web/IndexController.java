package com.shjang.portfolio.mall.web;

import com.shjang.portfolio.mall.config.auth.CustomOAuth2UserService;
import com.shjang.portfolio.mall.config.auth.LoginUser;
import com.shjang.portfolio.mall.config.auth.dto.SessionUser;
import com.shjang.portfolio.mall.domain.order.OrderComplete;
import com.shjang.portfolio.mall.service.art.ArtImageService;
import com.shjang.portfolio.mall.service.art.ArtService;
import com.shjang.portfolio.mall.service.art.CategoryService;
import com.shjang.portfolio.mall.service.order.OrderCompleteService;
import com.shjang.portfolio.mall.service.order.OrderService;
import com.shjang.portfolio.mall.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.Session;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final ArtService artService; //작품 Service
    private final CategoryService categoryService; //카테고리 Service
    private final CustomOAuth2UserService userService; //회원 Service
    private final OrderCompleteService orderCompleteService; //완료된 주문 Service

    //main 페이지
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {

        model.addAttribute("arts",artService.findAllDescToIndex());

        if (user != null) {
            model.addAttribute("userName",user.getName());
        }

        return "index";
    }

    //작품 등록 페이지로 단순 이동
    @GetMapping("/art/save")
    public String artSave(Model model, @LoginUser SessionUser user) {
        if (user != null) {
            model.addAttribute("userName",user.getName());
        }
        return "save";
    }

    //작품 수정 페이지로 Art 정보 가지고 이동
    @GetMapping("/art/update/{id}")
    public String artUpdate(@PathVariable Long id, Model model, @LoginUser SessionUser user) {

        if (user != null) {
            model.addAttribute("userName",user.getName());
        }

        ArtResponseDto dto = artService.findById(id);
        model.addAttribute("art", dto);

        return "update";
    }

    //작품 상세보기 페이지로 Art와 ArtImage 정보 가지고 이동
    @GetMapping("/art/detail/{id}")
    public String artDetail(@PathVariable Long id,Model model, @LoginUser SessionUser user) {

        if (user != null) {
            model.addAttribute("sessionUser",user);
            model.addAttribute("userName",user.getName());
        }

        ArtResponseDto art = artService.findById(id);
        model.addAttribute("art",art); //Art

        CategoryDto category = categoryService.findById(art.getCategoryId());

        model.addAttribute("category",category); //소분류

        if (category.getSuperId() == null) { //만약 선택 카테고리가 소분류가 없는 경우라면
            model.addAttribute("bigCategory",category); //해당 카테고리가 대분류로도 들어간다.
        } else {
            CategoryDto bigCategory = categoryService.findById(category.getSuperId());
            model.addAttribute("bigCategory",bigCategory); //대분류
        }

        UserResponseDto userResponseDto = new UserResponseDto(userService.findById(art.getUserId()));
        model.addAttribute("user",userResponseDto); //User
        return "detail";
    }

    //shop 페이지 (작품 리스트)
    @GetMapping("/art/list")
    public String artList(Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNum, @LoginUser SessionUser user) {

        List<ArtListResponseDto> artList = artService.findAllDesc(pageNum);
        Integer[] pageList = artService.getPageList(pageNum);

        if (user != null) {
            model.addAttribute("userName",user.getName());
        }

        model.addAttribute("pageList",pageList);
        model.addAttribute("arts",artList);

        //카테고리명을 '전체 작품'으로 직접 지정
        model.addAttribute("categoryName","전체 작품");

        //작품 수
        model.addAttribute("count",artService.count());

        return "list";
    }

    //shop 페이지 (카테고리로 조회)
    @GetMapping("/art/list/{category}")
    public String artListCate(Model model, @PathVariable int category, @LoginUser SessionUser user) {

        List<ArtListResponseDto> artList = artService.findAllDescCate(category);

        if (user != null) {
            model.addAttribute("userName",user.getName());
        }

        model.addAttribute("arts",artList);

        //카테고리명 찾기
        String cateName = categoryService.findById(category).getName();
        model.addAttribute("categoryName",cateName);

        //카테고리에 속해있는 Art 수
        model.addAttribute("count",artService.cateCount(category));

        return "list";
    }

    //mypage
    @GetMapping("/mypage")
    public String mypage(Model model, @LoginUser SessionUser user) {
        
        //불러온 주문 리스트의 모든 작품들을 넣기 위해
        List<OrderArtResponseDto> orderArtList = new ArrayList<>();

        //userId에 해당하는 orderComplete 리스트 불러오기
        List<OrderComplete> orderCompleteList = orderCompleteService.findOrderCompleteByUserId(user.getId());
        for (OrderComplete orderComplete : orderCompleteList) {

            //각각의 orderComplete 리스트의 Art id들을 가져오기
            List<Long> idList = orderComplete.getArtIdList();
            
            for (Long id : idList) {
                //가져온 Art id로 Art를 조회해 orderArtList에 주문날짜와 같이 넣기
                ArtResponseDto art = artService.findById(id);

                //LocalDateTime을 LocalDate로 변경
                LocalDateTime createdDate = orderComplete.getCreatedDate();
                LocalDate orderDate = LocalDate.from(createdDate);

                orderArtList.add(OrderArtResponseDto.builder()
                        .id(art.getId())
                        .title(art.getTitle())
                        .price(art.getPrice())
                        .artImage(art.getArtImage())
                        .orderDate(orderDate)
                        .build()
                );
            }
        }

        //OrderArtResponseDto에 주문날짜와 Art 합치기
        model.addAttribute("arts",orderArtList);

        if (user != null) {
            model.addAttribute("userName",user.getName());
        }

        return "mypage";
    }
}
