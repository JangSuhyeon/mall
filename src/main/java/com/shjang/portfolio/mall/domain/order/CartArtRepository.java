package com.shjang.portfolio.mall.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartArtRepository extends JpaRepository<CartArt,Long> {

    @Query(value = "SELECT art_id FROM cart_art WHERE cart_art.cart_id = ?1",nativeQuery = true)
    List<Long> findByCartId(Long cartId);

    @Query(value = "SELECT count(*) FROM cart_art WHERE cart_art.art_id = ?1",nativeQuery = true)
    Integer countByArtId(Long artId);

}
