package cn.iwenjuan.rest;

import cn.iwenjuan.rest.utils.IoUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author li1244
 * @date 2023/4/27 9:33
 */
public class UploadFile {

    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件大小
     */
    private long fileSize;
    /**
     * 文件内容
     */
    private byte[] content;

    public UploadFile(MultipartFile multipartFile) throws IOException {
        this.fileName = multipartFile.getOriginalFilename();
        this.fileSize = multipartFile.getSize();
        this.content = IoUtils.toBytes(multipartFile.getInputStream());
    }

    public UploadFile(File file) throws IOException {
        this.fileName = file.getName();
        this.content = IoUtils.toBytes(new FileInputStream(file));
        this.fileSize = this.content.length;
    }

    public UploadFile(String fileName, InputStream inputStream) throws IOException {
        this.fileName = fileName;
        this.content = IoUtils.toBytes(inputStream);
        this.fileSize = this.content.length;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public byte[] getContent() {
        return content;
    }
}
