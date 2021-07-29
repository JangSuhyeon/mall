package com.shjang.portfolio.mall.web;

import com.shjang.portfolio.mall.config.auth.LoginUser;
import com.shjang.portfolio.mall.config.auth.dto.SessionUser;
import com.shjang.portfolio.mall.domain.art.Art;
import com.shjang.portfolio.mall.service.art.ArtService;
import com.shjang.portfolio.mall.service.order.CartService;
import com.shjang.portfolio.mall.web.dto.ArtListResponseDto;
import com.shjang.portfolio.mall.web.dto.ArtResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final ArtService artService;
    private final CartService cartService;

    @GetMapping("/order/cart/{artId}")
    public String goToCart(@PathVariable Long artId, @LoginUser SessionUser users, Model model) {
        System.out.println("OrderController 도착!!");
        List<Long> artIdList = cartService.save(artId, users.getId()); //Cart안에 있는 Art Id List가져오기

        List<ArtResponseDto> art = new ArrayList<>();
        for (Long id : artIdList) { //Art Id를 통해서 Art 가져오기
            art.add(artService.findById(id));
        }

        model.addAttribute("arts",art);
        System.out.println("OrderController 종료!!");
        return "order/cart";
    }

}
