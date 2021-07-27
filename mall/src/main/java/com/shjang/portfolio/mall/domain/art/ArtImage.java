package com.shjang.portfolio.mall.domain.art;

import lombok.*;

import javax.persistence.*;

//작품 이미지
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArtImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //이미지 아이디

    @Column(nullable = false)
    private String filename; //이미지 파일 이름

    @Column(nullable = false)
    private String origFilename; //이미지 파일 원본 이름

    @Column(nullable = false)
    private String filePath; //이미지 저장 경로

    @Builder
    public ArtImage(String filename, String origFilename, String filePath) {
        this.filename = filename;
        this.origFilename = origFilename;
        this.filePath = filePath;
    }

    public void update(String filename, String origFilename, String filePath) {
        this.filename = filename;
        this.origFilename = origFilename;
        this.filePath = filePath;
    }
}
