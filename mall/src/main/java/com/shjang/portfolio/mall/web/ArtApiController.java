package com.shjang.portfolio.mall.web;

import com.shjang.portfolio.mall.config.auth.LoginUser;
import com.shjang.portfolio.mall.config.auth.dto.SessionUser;
import com.shjang.portfolio.mall.domain.art.Art;
import com.shjang.portfolio.mall.service.art.ArtImageService;
import com.shjang.portfolio.mall.service.art.ArtService;
import com.shjang.portfolio.mall.util.MD5Generator;
import com.shjang.portfolio.mall.web.dto.ArtResponseDto;
import com.shjang.portfolio.mall.web.dto.ArtSaveRequestDto;
import com.shjang.portfolio.mall.web.dto.ArtUpdateRequestDto;
import com.shjang.portfolio.mall.web.dto.FileRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;

@RequiredArgsConstructor
@RestController
public class ArtApiController {

    private final ArtService artService; //작품 서비스
    private final ArtImageService artImageService; //작품 이미지 서비스

    //작품 저장
    @PostMapping("/api/v1/art")
    public Long save(@RequestPart(value = "key") ArtSaveRequestDto requestDto,
                     @RequestPart(value = "file") MultipartFile files,
                     @LoginUser SessionUser user) {

        Long fileId = artImageService.saveFile(imageSave(files)); //이미지 파일 저장후 이미지의 id 반환
        requestDto.setArtImage(artImageService.getFileToArtImage(fileId)); //ArtImage객체를 Art에 넣기
        requestDto.setUserEmail(user.getEmail());

        return artService.save(requestDto);
    }

    //작품 수정
    @PutMapping("/api/v1/art/{id}")
    public Long update(@PathVariable Long id,
                       @RequestPart(value = "key") ArtUpdateRequestDto requestDto,
                       @RequestPart(value = "file") MultipartFile files) {

        Long fileId = artService.findById(requestDto.getId()).getArtImage().getId();
        System.out.println(fileId);

        if(files.isEmpty()){
            FileRequestDto  fileRequestDto = artImageService.getFile(fileId);
            artImageService.update(fileId,fileRequestDto);
        }else{
            artImageService.update(fileId,imageSave(files));
        }

        return artService.update(id, requestDto);
    }

    //작품 이미지 저장
    public FileRequestDto imageSave(MultipartFile files) {
        FileRequestDto fileRequestDto = new FileRequestDto();

        try {
            String origFilename = files.getOriginalFilename(); //원본 파일 이름
            String filename = new MD5Generator(origFilename).toString(); //저장된 파일 이름
            String savePath = "E:\\Spring Project\\mall\\src\\main\\resources\\static\\images"; //저장 경로

            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }

            String filePath = savePath + "\\" + filename;
            files.transferTo(new File(filePath));

            fileRequestDto.setOrigFilename(origFilename);
            fileRequestDto.setFilename(filename);
            fileRequestDto.setFilePath(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileRequestDto;
    }

    //id로 작품 찾기
    @GetMapping("/api/v1/art/{id}")
    public ArtResponseDto findById (@PathVariable Long id){
        return artService.findById(id);
    }

    //작품 삭제
    @DeleteMapping("/api/v1/art/{id}")
    public Long delete(@PathVariable Long id) {
        ArtResponseDto art  = artService.findById(id);
        artImageService.delete(art.getArtImage().getId()); //연결된 작품 이미지 삭제

        artService.delete(id); //작품 삭제

        return id;
    }

}
