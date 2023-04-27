package cn.iwenjuan.sample.controller;

import cn.iwenjuan.rest.UploadFile;
import cn.iwenjuan.rest.client.HttpClientBuilder;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.Map;

/**
 * @author li1244
 * @date 2023/4/26 17:13
 */
@RestController
@RequestMapping("storage")
@Slf4j
public class StorageController {

    @PostMapping("upload")
    public String upload(MultipartHttpServletRequest request) {
        MultipartFile file = request.getFile("file");
        String originalFilename = file.getOriginalFilename();
        log.info("文件上传：{}", originalFilename);
        Map<String, String[]> parameterMap = request.getParameterMap();
        log.info("文件上传参数：{}", JSON.toJSONString(parameterMap));
        return originalFilename;
    }

    @PostMapping("upload2")
    public String upload2(MultipartHttpServletRequest request) throws IOException {
        MultipartFile file = request.getFile("file");
        log.info("文件上传2：{}", file.getOriginalFilename());
        MultiValueMap<String, UploadFile> fileMultiValueMap = new LinkedMultiValueMap<>();
        fileMultiValueMap.add("file", new UploadFile(file));
        MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
        formData.add("name", "张三");
        String upload = HttpClientBuilder.create("http://localhost:8080/storage/upload").formData(formData).fileMap(fileMultiValueMap).build().doUpload();
        return upload;
    }
}
