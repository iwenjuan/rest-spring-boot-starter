package cn.iwenjuan.rest.client;

import cn.iwenjuan.rest.FileUpload;
import cn.iwenjuan.rest.RestRequestEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.Assert;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author li1244
 * @date 2023/3/23 17:12
 */
@Slf4j
public class HttpClient {

    private RestTemplate restTemplate;

    private RestRequestEntity requestEntity;

    public HttpClient(RestTemplate restTemplate, RestRequestEntity requestEntity) {
        Assert.notNull(restTemplate, "restTemplate can not be null");
        Assert.notNull(requestEntity, "requestEntity can not be null");
        this.restTemplate = restTemplate;
        this.requestEntity = requestEntity;
    }

    /**
     * 发送get请求
     * @return
     */
    public String doGet() {
        return doGet(String.class);
    }

    /**
     * 发送get请求
     * @return
     */
    public <T> T doGet(Class<T> responseType) {
        HttpHeaders httpHeaders = getHttpHeaders();
        HttpEntity httpEntity = new HttpEntity(null, httpHeaders);
        return exchange(httpEntity, HttpMethod.GET, responseType);
    }

    /**
     * 发送post请求
     * @return
     */
    public String doPost() {
        return doPost(String.class);
    }

    /**
     * 发送post请求
     * @return
     */
    public <T> T doPost(Class<T> responseType) {
        Object body = requestEntity.getBody();
        if (Objects.isNull(body)) {
            body = new HashMap<>(4);
        }
        HttpHeaders httpHeaders = getHttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity(body, httpHeaders);
        return exchange(httpEntity, HttpMethod.POST, responseType);
    }

    /**
     * 发送put请求
     * @return
     */
    public String doPut() {
        return doPut(String.class);
    }

    /**
     * 发送put请求
     * @param <T>
     * @return
     */
    public <T> T doPut(Class<T> responseType) {
        HttpHeaders httpHeaders = getHttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity(getBody(), httpHeaders);
        return exchange(httpEntity, HttpMethod.PUT, responseType);
    }

    /**
     * 发送delete请求
     * @return
     */
    public String doDelete() {
        return doDelete(String.class);
    }

    /**
     * 发送delete请求
     * @param <T>
     * @return
     */
    public <T> T doDelete(Class<T> responseType) {
        HttpHeaders httpHeaders = getHttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity(getBody(), httpHeaders);
        return exchange(httpEntity, HttpMethod.DELETE, responseType);
    }

    /**
     * 文件上传
     * @return
     */
    public String doUpload() {
        return doUpload(String.class);
    }

    /**
     * 文件上传
     * @return
     */
    public <T> T doUpload(Class<T> responseType) {
        HttpHeaders httpHeaders = getHttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> formData = getFormData();
        MultiValueMap<String, FileUpload> fileMap = requestEntity.getFileMap();
        if (fileMap == null) {
            fileMap = new LinkedMultiValueMap<>();
        }
        for (Map.Entry<String, List<FileUpload>> entry : fileMap.entrySet()) {
            String key = entry.getKey();
            for (FileUpload fileUpload : entry.getValue()) {
                formData.add(key, new ByteArrayResource(fileUpload.getContent()) {

                    @Override
                    public String getFilename() {
                        return fileUpload.getFileName();
                    }

                    @Override
                    public long contentLength() {
                        return fileUpload.getFileSize();
                    }
                });
            }
        }
        HttpEntity httpEntity = new HttpEntity(formData, httpHeaders);
        return exchange(httpEntity, HttpMethod.POST, responseType);
    }

    /**
     * form表单提交
     * @return
     */
    public String doForm() {
        return doForm(String.class);
    }

    /**
     * form表单提交
     * @return
     */
    public <T> T doForm(Class<T> responseType) {
        HttpHeaders httpHeaders = getHttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> formData = getFormData();
        HttpEntity httpEntity = new HttpEntity(formData, httpHeaders);
        return exchange(httpEntity, HttpMethod.POST, responseType);
    }



    /**
     * 发送请求
     * @param httpEntity
     * @param httpMethod
     * @param responseType
     * @return
     */
    private <T> T exchange(HttpEntity httpEntity, HttpMethod httpMethod, Class<T> responseType) {
        Assert.notNull(restTemplate, "restTemplate can not be null");
        ResponseEntity<T> responseEntity = restTemplate.exchange(getUrlWithParams(), httpMethod, httpEntity, responseType);
        T body = responseEntity.getBody();
        return body;
    }

    /**
     * 处理请求头
     * @return
     */
    private HttpHeaders getHttpHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        Map<String, String> headers = requestEntity.getHeaders();
        if (Objects.nonNull(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpHeaders.add(entry.getKey(), entry.getValue());
            }
        }
        return httpHeaders;
    }

    /**
     * 获取请求body
     * @return
     */
    private Object getBody() {
        Object body = requestEntity.getBody();
        if (Objects.isNull(body)) {
            body = new HashMap<>(4);
        }
        return body;
    }

    /**
     * 获取表单数据
     * @return
     */
    private MultiValueMap<String, Object> getFormData() {
        MultiValueMap<String, Object> formData = requestEntity.getFormData();
        if (formData == null) {
            formData = new LinkedMultiValueMap<>();
        }
        return formData;
    }

    /**
     * 处理请求参数
     * @return
     */
    private String getUrlWithParams() {
        String url = requestEntity.getUrl();
        Map<String, String[]> params = requestEntity.getParams();
        Assert.isTrue(isAvailableUrl(url), "url is not available：".concat(url));
        StringBuffer buffer = new StringBuffer();
        if (Objects.nonNull(params)) {
            for (Map.Entry<String, String[]> entry : params.entrySet()) {
                String key = entry.getKey();
                String[] values = entry.getValue();
                for (String value : values) {
                    buffer.append(key).append("=").append(value).append("&");
                }
            }
        }
        if (buffer.length() > 0) {
            String paramsStr = buffer.toString();
            url = url.concat("?").concat(paramsStr);
        }
        return url;
    }

    /**
     * 判断url是否可用
     * @param url
     * @return
     */
    private static boolean isAvailableUrl(String url) {
        if (url == null || url.trim().equals("")) {
            return false;
        }
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            return false;
        }
        return true;
    }

}
