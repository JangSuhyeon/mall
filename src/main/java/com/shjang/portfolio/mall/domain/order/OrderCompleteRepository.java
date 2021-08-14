package com.shjang.portfolio.mall.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderCompleteRepository extends JpaRepository<OrderComplete,Long> {

    @Query(value = "SELECT * FROM order_complete where order_complete.user_id = ?1",nativeQuery = true)
    OrderComplete findByUserId(Long userId); //User Id로 Order 가져오기

}
