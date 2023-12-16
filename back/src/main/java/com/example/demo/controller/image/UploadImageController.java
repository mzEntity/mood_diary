package com.example.demo.controller.image;


import com.example.demo.result.Result;
import com.example.demo.result.ResultFactory;
import com.example.demo.utils.MyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
public class UploadImageController {

    @PostMapping("/upload")
    private Result upload(MultipartFile fileUpload){
        String fileName = MyUtils.getCurrentTimestamp().getTime() + ".png";
        String filePath = MyUtils.projectDir + File.separator + "data" + File.separator + fileName;
        try{
            fileUpload.transferTo(new File(filePath));
            return ResultFactory.buildSuccessResult(new UploadImageResponsePackage(fileName));
        } catch (Exception e){
            return ResultFactory.buildFailResult(null, "error in save");
        }
    }

    private static class UploadImageResponsePackage{
        private String avatar;

        public UploadImageResponsePackage(String avatar) {
            this.avatar = avatar;
        }

        public String getAvatar() {
            return avatar;
        }
    }
}
