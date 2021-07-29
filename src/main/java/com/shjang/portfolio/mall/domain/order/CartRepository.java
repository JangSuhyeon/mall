package com.shjang.portfolio.mall.domain.order;

import com.shjang.portfolio.mall.domain.art.Art;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value = "SELECT * FROM cart where cart.user_id = ?1",nativeQuery = true)
    Cart findByUserId(Long usrId); //User Id로 Cart 가져오기

    @Query(value = "SELECT count(*) FROM cart where cart.user_id = ?1",nativeQuery = true)
    Integer countByUserId(Long usrId);

    @Query(value = "SELECT count(*) FROM arts_in_cart where arts_in_cart.art_id = ?1",nativeQuery = true)
    Integer countByArtId(Long artId);

}
