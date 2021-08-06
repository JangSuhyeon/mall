package com.shjang.portfolio.mall.web;

import com.shjang.portfolio.mall.config.auth.LoginUser;
import com.shjang.portfolio.mall.config.auth.dto.SessionUser;
import com.shjang.portfolio.mall.domain.art.Art;
import com.shjang.portfolio.mall.domain.order.Cart;
import com.shjang.portfolio.mall.domain.order.CartArt;
import com.shjang.portfolio.mall.domain.order.OrderEntity;
import com.shjang.portfolio.mall.service.art.ArtService;
import com.shjang.portfolio.mall.service.order.CartService;
import com.shjang.portfolio.mall.service.order.OrderService;
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
    private final OrderService orderService;

    // Detail페이지에서 장바구니 담았을 때
    @GetMapping("/order/cart/{artId}")
    public String addToCart(@PathVariable Long artId, @LoginUser SessionUser user, Model model) {

        Cart cart = cartService.findByUserId(user.getId());
        ArtResponseDto artResponseDto = artService.findById(artId);
        Art art = Art.builder()
                .title(artResponseDto.getTitle())
                .artist(artResponseDto.getArtist())
                .description(artResponseDto.getDescription())
                .price(artResponseDto.getPrice())
                .userId(artResponseDto.getUserId())
                .artImage(artResponseDto.getArtImage())
                .categoryId(artResponseDto.getCategoryId())
                .build();
        cartService.addToCart(new CartArt(cart,art));

        return "order/cart";
    }

    //메인메뉴에서 바로 Cart 메뉴로 들어갈 때
    @GetMapping("/order/cart")
    public String goToCart(@LoginUser SessionUser user, Model model) {

        /*Cart cart = cartService.findByUserId(user.getId()); //해당 유저의 Cart가져오기
        List<Long> artIdList = cart.getArtIdList(); //가져온 Cart안에 ArtId 가져오기

        List<ArtResponseDto> art = new ArrayList<>();
        for (Long id : artIdList) { //Art Id를 통해서 Art 가져오기
            art.add(artService.findById(id));
        }

        model.addAttribute("arts",art);

        if (user != null) {
            model.addAttribute("userId", user.getId());
        }*/

        return "order/cart";
    }

    @GetMapping("/order/order")
    @ResponseBody
    public void addToOrder(@RequestParam(value = "art_id[]") List<Long> artIdList,@LoginUser SessionUser user) {

        //Cart에 담긴 Art만 Order 목록에 올리기 위해 *기존* 해당 User의 Order 목록 삭제
        orderService.deleteOrderEntityByUserId(user.getId());
        orderService.createOrder(user.getId()); //주문 생성

        for (Long artId : artIdList) {
            orderService.save(artId,user.getId());
        }

    }

    @GetMapping("/order/order/{id}")
    public String goToOrder(@PathVariable Long id,Model model,@LoginUser SessionUser user) {

        OrderEntity order = orderService.findByUserId(user.getId());
        List<Long> artIdList = order.getArtIdList(); //가져온 Cart안에 ArtId 가져오기

        List<ArtResponseDto> art = new ArrayList<>();
        for (Long artId : artIdList) { //Art Id를 통해서 Art 가져오기
            art.add(artService.findById(artId));
        }

        model.addAttribute("arts",art);
        
        return "order/order";
    }

}
