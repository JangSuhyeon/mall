package com.shjang.portfolio.mall.service.order;

import com.shjang.portfolio.mall.domain.order.Cart;
import com.shjang.portfolio.mall.domain.order.OrderEntity;
import com.shjang.portfolio.mall.domain.order.OrderEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderEntityRepository orderRepository;

    @Transactional
    public Long createOrder(Long userId) {
        return orderRepository.save(new OrderEntity(userId)).getId();
    }

    @Transactional
    public List<Long> save(Long artId, Long userId) {
        OrderEntity order = orderRepository.findByUserId(userId);
        return order.addToOrder(artId);
    }

    @Transactional
    public Integer countByUserId(Long userId) {
        return orderRepository.countByUserId(userId);
    }

    @Transactional
    public OrderEntity findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Transactional
    public void deleteOrderEntityByUserId(Long userId) {
        orderRepository.deleteOrderEntityByUserId(userId);
    }

}
