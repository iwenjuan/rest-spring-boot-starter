package cn.iwenjuan.rest;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.util.MultiValueMap;

import java.util.Map;

/**
 * @author li1244
 * @date 2023/4/27 14:03
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class RestRequestEntity {

    /**
     * 请求地址
     */
    private String url;
    /**
     * 请求参数（url参数）
     */
    private Map<String, String[]> params;
    /**
     * 请求头
     */
    private Map<String, String> headers;
    /**
     * 请求body
     */
    private Object body;
    /**
     * 表单信息
     */
    private MultiValueMap<String, Object> formData;
    /**
     * 文件上传信息
     */
    private MultiValueMap<String, UploadFile> fileMap;
}
