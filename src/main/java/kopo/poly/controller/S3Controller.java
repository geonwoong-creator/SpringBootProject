package kopo.poly.controller;

import kopo.poly.service.impl.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@RestController
@Slf4j
@RequiredArgsConstructor
public class S3Controller {
    private final S3Service s3Service;


    @PostMapping("/images")
    public String upload(@RequestParam("images") MultipartFile multipartFile) throws IOException {

        String rStr = s3Service.uploadImageInS3(multipartFile, "static");

        return rStr;
    }
}

