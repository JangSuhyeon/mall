package com.shjang.portfolio.mall.config.auth.dto;

import com.shjang.portfolio.mall.domain.user.User;
import lombok.Getter;

import javax.mail.Session;
import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private Long id;
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}