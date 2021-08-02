package com.shjang.portfolio.mall.domain.order;

import com.shjang.portfolio.mall.domain.art.Art;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderEntityRepository extends JpaRepository<OrderEntity,Long> {

    @Query(value = "SELECT * FROM order_entity where order_entity.user_id = ?1",nativeQuery = true)
    OrderEntity findByUserId(Long userId); //User Id로 Order 가져오기

    @Query(value = "SELECT count(*) FROM order_entity where order_entity.user_id = ?1",nativeQuery = true)
    Integer countByUserId(Long artId);

    void deleteOrderEntityByUserId(Long userId);

}
