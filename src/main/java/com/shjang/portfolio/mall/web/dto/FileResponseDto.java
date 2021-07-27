package com.shjang.portfolio.mall.web.dto;

import com.shjang.portfolio.mall.domain.art.ArtImage;
import lombok.Data;
import lombok.NoArgsConstructor;

//작품 이미지를 불러올 때 사용
@Data
@NoArgsConstructor
public class FileResponseDto {
    private Long id;
    private String origFilename;
    private String filename;
    private String filePath;

    public ArtImage toEntity() { //??
        ArtImage build = ArtImage.builder()
                .origFilename(origFilename)
                .filename(filename)
                .filePath(filePath)
                .build();

        return build;
    }

    public FileResponseDto(ArtImage entity) {
        this.id = entity.getId();
        this.origFilename = entity.getOrigFilename();
        this.filename = entity.getFilename();
        this.filePath = entity.getFilePath();
    }
}
