package com.shjang.portfolio.mall.service.art;

import com.shjang.portfolio.mall.domain.art.Art;
import com.shjang.portfolio.mall.domain.art.ArtRepository;
import com.shjang.portfolio.mall.web.dto.ArtListResponseDto;
import com.shjang.portfolio.mall.web.dto.ArtResponseDto;
import com.shjang.portfolio.mall.web.dto.ArtSaveRequestDto;
import com.shjang.portfolio.mall.web.dto.ArtUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArtService {

    private final ArtRepository artRepository;

    //작품 저장
    @Transactional
    public Long save(ArtSaveRequestDto requestDto){
        return artRepository.save(requestDto.toEntity()).getId();
    }

    //작품 수정
    @Transactional
    public Long update(Long id, ArtUpdateRequestDto requestDto){
        Art art = artRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 작품이 없습니다. id=" + id));

        art.update(requestDto.getTitle(), requestDto.getArtist(), requestDto.getDescription(), requestDto.getPrice(), findById(requestDto.getId()).getArtImage());

        return id;
    }

    //작품 id로 작품 찾기
    public ArtResponseDto findById (Long id){
        Art entity = artRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 작품이 없습니다. id=" + id));

        return new ArtResponseDto(entity);
    }

    //작품 전체 찾기 
    @Transactional(readOnly = true)
    public List<ArtListResponseDto> findAllDesc() {
        return artRepository.findAllDesc().stream().map(ArtListResponseDto::new).collect(Collectors.toList());
    }

    //작품 삭제
    @Transactional
    public void delete(Long id) {
        Art art = artRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        artRepository.delete(art);
    }

    //작품 총 갯수
    @Transactional
    public Long count() {
        return artRepository.count();
    }

}
