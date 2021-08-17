package com.shjang.portfolio.mall.service.order;

import com.shjang.portfolio.mall.domain.order.OrderComplete;
import com.shjang.portfolio.mall.domain.order.OrderCompleteRepository;
import com.shjang.portfolio.mall.domain.order.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderCompleteService {

    private final OrderCompleteRepository orderCompleteRepository;

    @Transactional
    public Long createOrderComplete(Long userId) {
        return orderCompleteRepository.save(new OrderComplete(userId)).getId();
    }

    @Transactional
    public List<Long> save(Long artId, Long userId) {
        OrderComplete orderComplete = orderCompleteRepository.findByUserId(userId);
        return orderComplete.addToOrderComplete(artId);
    }

    @Transactional
    public List<OrderComplete> findOrderCompleteByUserId(Long userId) {
        //userId에 해당하는 완료된 주문 리스트 불러오기
        return orderCompleteRepository.findByUserIdToList(userId);
    }

}