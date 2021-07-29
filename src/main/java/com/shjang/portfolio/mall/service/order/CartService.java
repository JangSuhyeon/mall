package com.shjang.portfolio.mall.service.order;

import com.shjang.portfolio.mall.domain.order.Cart;
import com.shjang.portfolio.mall.domain.order.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;

    @Transactional
    public Long createCart(Long userId) {
        return cartRepository.save(new Cart(userId)).getId();
    }

    @Transactional
    public List<Long> save(Long artId,Long userId) {
        Cart cart  = cartRepository.findByUserId(userId); //해당 유저의 Cart 가져오기
        return cart.addToCart(artId); //Cart에 Art Id INSERT
    }

    @Transactional
    public Cart findByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Transactional
    public Integer countByUserId(Long userId) {
        return cartRepository.countByUserId(userId);
    }

    @Transactional
    public Integer countByArtId(Long artId) {
        return cartRepository.countByArtId(artId);
    }

}
