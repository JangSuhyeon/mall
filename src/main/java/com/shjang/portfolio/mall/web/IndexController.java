package com.shjang.portfolio.mall.web;

import com.shjang.portfolio.mall.config.auth.CustomOAuth2UserService;
import com.shjang.portfolio.mall.config.auth.LoginUser;
import com.shjang.portfolio.mall.config.auth.dto.SessionUser;
import com.shjang.portfolio.mall.service.art.ArtImageService;
import com.shjang.portfolio.mall.service.art.ArtService;
import com.shjang.portfolio.mall.service.art.CategoryService;
import com.shjang.portfolio.mall.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final ArtService artService; //작품 Service
    private final CategoryService categoryService; //카테고리 Service
    private final CustomOAuth2UserService userService; //회원 Service

    //main 페이지
    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user) {

        model.addAttribute("arts",artService.findAllDesc());

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
        }

        ArtResponseDto art = artService.findById(id);
        model.addAttribute("art",art); //Art

        CategoryDto category = categoryService.findById(art.getCategoryId());
        model.addAttribute("category",category); //소분류
        CategoryDto bigCategory = categoryService.findById(category.getSuperId());
        model.addAttribute("bigCategory",bigCategory); //대분류

        UserResponseDto userResponseDto = new UserResponseDto(userService.findById(art.getUserId()));
        model.addAttribute("user",userResponseDto); //User
        return "detail";
    }

    //shop 페이지 (작품 리스트)
    @GetMapping("/art/list")
    public String artList(Model model, @LoginUser SessionUser user) {

        if (user != null) {
            model.addAttribute("userName",user.getName());
        }

        model.addAttribute("arts",artService.findAllDesc());

        //작품 수
        model.addAttribute("count",artService.count());

        return "list";
    }
}
