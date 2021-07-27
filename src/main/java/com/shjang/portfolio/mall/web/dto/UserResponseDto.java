package com.shjang.portfolio.mall.web.dto;

import com.shjang.portfolio.mall.domain.art.Art;
import com.shjang.portfolio.mall.domain.user.Role;
import com.shjang.portfolio.mall.domain.user.User;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
public class UserResponseDto {
    private Long id;
    private String name;
    private String email;
    private String picture;
    private Role role;

    public UserResponseDto(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.picture = entity.getPicture();
        this.role = entity.getRole();
    }
}
