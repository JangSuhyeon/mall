package com.shjang.portfolio.mall.domain.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Getter
@NoArgsConstructor
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId; //cart user

    @ElementCollection
    @CollectionTable(name="arts_in_cart")
    private List<Long> artIdList = new ArrayList<>(); //cart에 담은 Art들의 id

    public Cart(Long userId) {
        this.userId = userId;
    }

    public List<Long> addToCart(Long artId) {
        artIdList.add(artId);

        return artIdList;
    }
}