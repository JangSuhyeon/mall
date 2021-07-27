package com.shjang.portfolio.mall.service.art;

import com.shjang.portfolio.mall.domain.art.Art;
import com.shjang.portfolio.mall.domain.art.ArtImage;
import com.shjang.portfolio.mall.domain.art.ArtImageRepository;
import com.shjang.portfolio.mall.web.dto.ArtUpdateRequestDto;
import com.shjang.portfolio.mall.web.dto.FileRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ArtImageService {

    private ArtImageRepository artImageRepository;

    public ArtImageService(ArtImageRepository artImageRepository) {
        this.artImageRepository = artImageRepository;
    }

    //작품 이미지 저장
    @Transactional
    public Long saveFile(FileRequestDto fileRequestDto) {
        return artImageRepository.save(fileRequestDto.toEntity()).getId();
    }

    //작품이미지 id로 작품이미지 찾기 : return FileRequestDto
    @Transactional
    public FileRequestDto getFile(Long id) {
        ArtImage file = artImageRepository.findById(id).get();

        FileRequestDto fileRequestDto = FileRequestDto.builder()
                .origFilename(file.getOrigFilename())
                .filename(file.getFilename())
                .filePath(file.getFilePath())
                .build();

        return fileRequestDto;
    }

    //작품이미지 id로 작품이미지 찾기 : return ArtImage
    @Transactional
    public ArtImage getFileToArtImage(Long id) {

        return artImageRepository.findById(id).get();
    }

    //작품 이미지 수정
    @Transactional
    public Long update(Long id, FileRequestDto requestDto){
        ArtImage image = artImageRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 이미지가 없습니다. id=" + id));

        image.update(requestDto.getFilename(), requestDto.getOrigFilename(), requestDto.getFilePath());

        return id;
    }

    //작품 이미지 삭제
    @Transactional
    public void delete(Long id) {
        ArtImage image = artImageRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 이미지가 없습니다. id="+id));

        artImageRepository.delete(image);
    }
}
