package com.shjang.portfolio.mall.domain.order;

import com.shjang.portfolio.mall.domain.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class OrderComplete extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ElementCollection
    @CollectionTable(name="arts_in_orderComplete")
    private List<Long> artIdList = new ArrayList<>(); //주문할 담은 Art들의 id

    public OrderComplete(Long userId) {
        this.userId = userId;
    }

    public List<Long> addToOrderComplete(Long artId) {

        artIdList.add(artId);

        return artIdList;
    }

}
