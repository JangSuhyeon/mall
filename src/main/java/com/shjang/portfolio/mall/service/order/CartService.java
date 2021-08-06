package com.shjang.portfolio.mall.service.order;

import com.shjang.portfolio.mall.domain.art.Art;
import com.shjang.portfolio.mall.domain.order.Cart;
import com.shjang.portfolio.mall.domain.order.CartArt;
import com.shjang.portfolio.mall.domain.order.CartArtRepository;
import com.shjang.portfolio.mall.domain.order.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartArtRepository cartArtRepository;

    //User의 Cart가 이미 생성되어있는지 확인
    @Transactional
    public Integer countByUserId(Long userId) {
        return cartRepository.countByUserId(userId);
    }

    //User가 회원가입시 Cart 생성
    @Transactional
    public Cart createCart(Long userId) {
        return cartRepository.save(new Cart(userId));
    }

    //등록하려는 Art가 이미 추가되어있는지 확인
    @Transactional
    public Integer countByArtId(Long artId) {
        return cartArtRepository.countByArtId(artId);
    }

    //UserId로 Cart 찾기
    @Transactional
    public Cart findByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    //Cart에 Art 추가
    @Transactional
    public Long addToCart(CartArt cartArt) {
        return cartArtRepository.save(cartArt).getId();
    }

    //Cart 안에 Art 모두 가져오기
    @Transactional
    public List<Long> findAllArt(Long cartId) {
        return cartArtRepository.findByCartId(cartId);
    }

}
