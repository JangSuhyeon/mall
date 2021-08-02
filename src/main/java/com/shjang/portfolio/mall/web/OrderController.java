package com.shjang.portfolio.mall.web;

import com.shjang.portfolio.mall.config.auth.LoginUser;
import com.shjang.portfolio.mall.config.auth.dto.SessionUser;
import com.shjang.portfolio.mall.domain.art.Art;
import com.shjang.portfolio.mall.domain.order.Cart;
import com.shjang.portfolio.mall.service.art.ArtService;
import com.shjang.portfolio.mall.service.order.CartService;
import com.shjang.portfolio.mall.web.dto.ArtListResponseDto;
import com.shjang.portfolio.mall.web.dto.ArtResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final ArtService artService;
    private final CartService cartService;

    // Detail페이지에서 장바구니 담았을 때
    @GetMapping("/order/cart/{artId}")
    public String addToCart(@PathVariable Long artId, @LoginUser SessionUser users, Model model) {

        if(cartService.countByArtId(artId) <= 0){ //담으려는 Art가 장바구니에 없으면 추가
            cartService.save(artId, users.getId()); //Cart안에 있는 Art Id List가져오기
        }

        Cart cart = cartService.findByUserId(users.getId()); //해당 유저의 Cart가져오기
        List<Long> artIdList = cart.getArtIdList(); //가져온 Cart안에 ArtId 가져오기

        List<ArtResponseDto> art = new ArrayList<>();
        for (Long id : artIdList) { //Art Id를 통해서 Art 가져오기
            art.add(artService.findById(id));
        }

        model.addAttribute("arts",art);

        return "order/cart";
    }

    //메인메뉴에서 바로 Cart 메뉴로 들어갈 때
    @GetMapping("/order/cart")
    public String goToCart(@LoginUser SessionUser users, Model model) {

        Cart cart = cartService.findByUserId(users.getId()); //해당 유저의 Cart가져오기
        List<Long> artIdList = cart.getArtIdList(); //가져온 Cart안에 ArtId 가져오기

        List<ArtResponseDto> art = new ArrayList<>();
        for (Long id : artIdList) { //Art Id를 통해서 Art 가져오기
            art.add(artService.findById(id));
        }

        model.addAttribute("arts",art);

        return "order/cart";
    }

    @GetMapping("/order/order")
    @ResponseBody
    public List<ArtResponseDto> goToOrder(@RequestParam(value = "art_id[]") List<Long> artIdList, Model model) {

        List<ArtResponseDto> arts = new ArrayList<>();
        for (Long id : artIdList) { //Art Id를 통해서 Art 가져오기
            arts.add(artService.findById(id));
        }

        return arts;

    }

}
