package com.shjang.portfolio.mall.domain.order;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class OrderEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long userId;

    @ElementCollection
    @CollectionTable(name="arts_in_order")
    private List<Long> artIdList = new ArrayList<>(); //주문할 담은 Art들의 id

    public OrderEntity(Long userId) {
        this.userId = userId;
    }

    public List<Long> addToOrder(Long artId) {

        artIdList.add(artId);

        return artIdList;
    }
}
