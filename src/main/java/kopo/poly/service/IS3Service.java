package kopo.poly.service;

import org.springframework.web.multipart.MultipartFile;

public interface IS3Service {
    String uploadImageInS3(MultipartFile multipartFile, String dirName) throws Exception;
}
