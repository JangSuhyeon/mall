package com.shjang.portfolio.mall.web;

import com.shjang.portfolio.mall.config.auth.LoginUser;
import com.shjang.portfolio.mall.config.auth.dto.SessionUser;
import com.shjang.portfolio.mall.domain.art.Art;
import com.shjang.portfolio.mall.domain.art.ArtImage;
import com.shjang.portfolio.mall.service.art.ArtImageService;
import com.shjang.portfolio.mall.service.art.ArtService;
import com.shjang.portfolio.mall.service.order.CartService;
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
public class
ArtApiController {

    private final ArtService artService; //작품 서비스
    private final ArtImageService artImageService; //작품 이미지 서비스
    private final CartService cartService; //장바구니 서비스

    //작품 저장
    @PostMapping("/api/v1/art")
    public Long save(@RequestPart(value = "key") ArtSaveRequestDto requestDto,
                     @RequestPart(value = "file") MultipartFile files,
                     @LoginUser SessionUser user) {
        System.out.println("!!!!!!!!!!category!!!" + requestDto.getCategoryId());
        Long fileId = artImageService.saveFile(imageSave(files));

        requestDto.setArtImage(artImageService.findById(fileId));
        requestDto.setUserId(user.getId());

        return artService.save(requestDto);
    }

    //작품 수정
    @PutMapping("/api/v1/art/{id}")
    public Long update(@PathVariable Long id,
                       @RequestPart(value = "key") ArtUpdateRequestDto requestDto,
                       @RequestPart(value = "file") MultipartFile files) {

        Long artImageId = requestDto.getArtImageId();

        if(files.isEmpty()){
            artImageService.update(artImageId, artImageService.getFile(artImageId));
        }else{
            artImageService.update(artImageId,imageSave(files));
        }

        return artService.update(id, requestDto);
    }

    //작품 이미지 저장
    public FileRequestDto imageSave(MultipartFile files) {
        FileRequestDto fileRequestDto = new FileRequestDto();

        try {
            String origFilename = files.getOriginalFilename(); //원본 파일 이름
            String filename = new MD5Generator(origFilename).toString(); //저장된 파일 이름
            String rootPath = System.getProperty("user.dir"); //현재 프로젝트 경로
            String thumbPath = "\\src\\main\\resources\\static\\images\\thumbnail"; //썸네일 이미지 파일 경로
            String savePath = rootPath + thumbPath; //저장 경로

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

        cartService.deleteCart(id); //장바구니 삭제
        artService.delete(id); //작품 삭제
        artImageService.delete(art.getArtImage().getId()); //연결된 작품 이미지 삭제

        return id;
    }

}
