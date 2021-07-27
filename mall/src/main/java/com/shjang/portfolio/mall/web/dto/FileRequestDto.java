package com.shjang.portfolio.mall.web.dto;

import com.shjang.portfolio.mall.domain.art.ArtImage;
import lombok.*;

import java.io.File;

//작품 이미지를 저장/수정할 때 사용
@Data
@NoArgsConstructor
public class FileRequestDto {
    private String origFilename;
    private String filename;
    private String filePath;

    public ArtImage toEntity() {
        ArtImage build = ArtImage.builder()
                .origFilename(origFilename)
                .filename(filename)
                .filePath(filePath)
                .build();

        return build;
    }

    @Builder
    public FileRequestDto(String origFilename, String filename, String filePath) {
        this.origFilename = origFilename;
        this.filename = filename;
        this.filePath = filePath;
    }
}
