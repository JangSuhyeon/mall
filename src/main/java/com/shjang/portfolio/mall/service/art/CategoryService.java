package com.shjang.portfolio.mall.service.art;

import com.shjang.portfolio.mall.domain.art.Art;
import com.shjang.portfolio.mall.domain.art.Category;
import com.shjang.portfolio.mall.domain.art.CategoryRepository;
import com.shjang.portfolio.mall.web.dto.ArtResponseDto;
import com.shjang.portfolio.mall.web.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryDto findById(int id) {
        Category entity = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 분류가 없습니다. id=" + id));

        return new CategoryDto(entity);
    }

}
