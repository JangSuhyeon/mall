package com.shjang.portfolio.mall.service.art;

import com.shjang.portfolio.mall.domain.art.Art;
import com.shjang.portfolio.mall.domain.art.ArtRepository;
import com.shjang.portfolio.mall.web.dto.ArtListResponseDto;
import com.shjang.portfolio.mall.web.dto.ArtResponseDto;
import com.shjang.portfolio.mall.web.dto.ArtSaveRequestDto;
import com.shjang.portfolio.mall.web.dto.ArtUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArtService {

    private final ArtRepository artRepository;
    private static final int BLOCK_PAGE_NUM_COUNT = 5; //블럭에 존재하는 페이지 수
    private static final int PAGE_POST_COUNT = 6; //한 페이지에 존재하는 게시글 수

    //작품 전체 찾기 (Index - page가 필요없을 경우)
    @Transactional(readOnly = true)
    public List<ArtListResponseDto> findAllDescToIndex() {
        List<ArtListResponseDto> artListResponseDtos = artRepository.findAllDesc().stream().map(ArtListResponseDto::new).collect(Collectors.toList());
        for (ArtListResponseDto dto : artListResponseDtos) {
            System.out.println(dto.getId() + "!!!");
        }

        return artRepository.findAllDesc().stream().map(ArtListResponseDto::new).collect(Collectors.toList());
    }

    //작품 전체 찾기 (Shop - page가 필요할 경우)
    @Transactional(readOnly = true)
    public List<ArtListResponseDto> findAllDesc(Integer pageNum) {

        Page<Art> page = artRepository.findAll(PageRequest
        .of(pageNum-1, PAGE_POST_COUNT, Sort.by(Sort.Direction.ASC,"createdDate")));

        List<Art> arts = page.getContent();
        List<ArtListResponseDto> artListResponseDtos = new ArrayList<>();
        for (Art art : arts) {
            artListResponseDtos.add(new ArtListResponseDto(art));
        }

        return artListResponseDtos;
    }

    //특정 카테고리의 작품 찾기 (Shop - page가 필요없는 경우)
    @Transactional(readOnly = true)
    public List<ArtListResponseDto> findAllDescCate(int category) {

        List<Art> arts = artRepository.findAllCate(category);
        List<ArtListResponseDto> artListResponseDtos = new ArrayList<>();
        for (Art art : arts) {
            artListResponseDtos.add(new ArtListResponseDto(art));
        }

        return artListResponseDtos;
    }


    public Integer[] getPageList(Integer curPageNum) {
        Integer[] pageList = new Integer[BLOCK_PAGE_NUM_COUNT];

        //총 게시글 수
        Double postsTotalCount = Double.valueOf(this.getBoardCount());

        //총 개시글 수를 기준으로 계산한 마지막 페이지 번호 계산
        Integer totalLastPageNum = (int)(Math.ceil((postsTotalCount/PAGE_POST_COUNT)));

        //현재 페이지를 기준으로 블럭의 마지막 페이지 번호 계산
        Integer blockLastPageNum = (totalLastPageNum > curPageNum + BLOCK_PAGE_NUM_COUNT)
                ? curPageNum + BLOCK_PAGE_NUM_COUNT
                : totalLastPageNum;

        //페이지 시작 번호 조정
        curPageNum = (curPageNum<=3) ? 1 : curPageNum-2;

        //페이지 번호 할당
        for (int val=curPageNum, i=0; val<=blockLastPageNum; val++, i++) {
            pageList[i] = val;
        }

        return pageList;
    }

    @Transactional
    public Long getBoardCount() {
        return artRepository.count();
    }

    //작품 저장
    @Transactional
    public Long save(ArtSaveRequestDto requestDto){
        return artRepository.save(requestDto.toEntity()).getId();
    }

    //작품 수정
    @Transactional
    public Long update(Long id, ArtUpdateRequestDto requestDto){
        Art art = artRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 작품이 없습니다. id=" + id));

        art.update(requestDto.getTitle(), requestDto.getArtist(), requestDto.getDescription(), requestDto.getPrice(), requestDto.getCategoryId());

        return id;
    }

    //작품 id로 작품 찾기 (return ArtResponseDto)
    public ArtResponseDto findById (Long id){
        Art entity = artRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 작품이 없습니다. id=" + id));

        return new ArtResponseDto(entity);
    }

    //작품 id로 작품 찾기 (return Art)
    public Art findByIdToArt (Long id){
        Art entity = artRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 작품이 없습니다. id=" + id));

        return entity;
    }

    //작품 삭제
    @Transactional
    public void delete(Long id) {
        Art art = artRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        artRepository.delete(art);
    }

    //전체 작품 총 갯수
    @Transactional
    public Long count() {
        return artRepository.count();
    }

    //작품 총 갯수
    @Transactional
    public Integer cateCount(int categoryId) {
        return artRepository.cateCount(categoryId);
    }

}
