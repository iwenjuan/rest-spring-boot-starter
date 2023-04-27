package cn.iwenjuan.rest.client;

import cn.iwenjuan.rest.FileUpload;
import cn.iwenjuan.rest.config.RestTemplateBuilder;
import cn.iwenjuan.rest.context.SpringApplicationContext;
import cn.iwenjuan.rest.enums.ClientType;
import cn.iwenjuan.rest.properties.UrlConnectionProperties;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
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
public class HttpClient {

    private static RestTemplate restTemplate;

    static {
        restTemplate = SpringApplicationContext.getBean("restTemplate");
        if (restTemplate == null) {
            restTemplate = RestTemplateBuilder.create()
                    .clientType(ClientType.URLConnection)
                    .urlConnection(new UrlConnectionProperties())
                    .build();
        }
    }

    /**
     * 发送get请求
     * @param url   请求地址
     * @return
     */
    public static String doGet(String url) {
        return doGet(url, String.class);
    }

    /**
     * 发送get请求
     * @param url           请求地址
     * @param responseType  返回结果类型
     * @return
     */
    public static <T> T doGet(String url, Class<T> responseType) {
        return doGet(url, null, responseType);
    }

    /**
     * 发送get请求
     * @param url       请求地址
     * @param params    请求参数
     * @return
     */
    public static String doGet(String url, Map<String, String[]> params) {
        return doGet(url, params, String.class);
    }

    /**
     * 发送get请求
     * @param url           请求地址
     * @param params        请求参数
     * @param responseType  返回结果类型
     * @return
     */
    public static <T> T doGet(String url, Map<String, String[]> params, Class<T> responseType) {
        return doGetWithHeaders(url, params, null, responseType);
    }

    /**
     * 发送get请求
     * @param url       请求地址
     * @param headers   请求头
     * @return
     */
    public static String doGetWithHeaders(String url, Map<String, String> headers) {
        return doGetWithHeaders(url, headers, String.class);
    }

    /**
     * 发送get请求
     * @param url           请求地址
     * @param headers       请求头
     * @param responseType  返回结果类型
     * @return
     */
    public static <T> T doGetWithHeaders(String url, Map<String, String> headers, Class<T> responseType) {
        return doGetWithHeaders(url, null, headers, responseType);
    }

    /**
     * 发送get请求
     * @param url       请求地址
     * @param params    请求参数
     * @param headers   请求头
     * @return
     */
    public static String doGetWithHeaders(String url, Map<String, String[]> params, Map<String, String> headers) {
        return doGetWithHeaders(url, params, headers, String.class);
    }

    /**
     * 发送get请求
     * @param url           请求地址
     * @param params        请求参数
     * @param headers       请求头
     * @param responseType  返回结果类型
     * @return
     */
    public static <T> T doGetWithHeaders(String url, Map<String, String[]> params, Map<String, String> headers, Class<T> responseType) {

        HttpHeaders httpHeaders = getHttpHeaders(headers);
        HttpEntity httpEntity = new HttpEntity(null, httpHeaders);
        ResponseEntity<T> responseEntity = restTemplate.exchange(getUrlWithParams(url, params), HttpMethod.GET, httpEntity, responseType);
        return responseEntity.getBody();
    }

    /**
     * 发送post请求
     * @param url   请求地址
     * @return
     */
    public static String doPost(String url) {
        return doPost(url, String.class);
    }

    /**
     * 发送post请求
     * @param url           请求地址
     * @param responseType  返回结果类型
     * @return
     */
    public static <T> T doPost(String url, Class<T> responseType) {
        return doPost(url, null, null, responseType);
    }

    /**
     * 发送post请求
     * @param url       请求地址
     * @param body      请求参数（body参数）
     * @return
     */
    public static String doPost(String url, Object body) {
        return doPost(url, body, String.class);
    }

    /**
     * 发送post请求
     * @param url           请求地址
     * @param body          请求参数（body参数）
     * @param responseType  返回结果类型
     * @return
     */
    public static <T> T doPost(String url, Object body, Class<T> responseType) {
        return doPost(url, null, body, responseType);
    }

    /**
     * 发送post请求
     * @param url       请求地址
     * @param params    请求参数（url参数）
     * @param body      请求参数（body参数）
     * @return
     */
    public static String doPost(String url, Map<String, String[]> params, Object body) {
        return doPost(url, params, body, String.class);
    }

    /**
     * 发送post请求
     * @param url           请求地址
     * @param params        请求参数（url参数）
     * @param body          请求参数（body参数）
     * @param responseType  返回结果类型
     * @return
     */
    public static <T> T doPost(String url, Map<String, String[]> params, Object body, Class<T> responseType) {
        return doPostWithHeaders(url, params, body, null, responseType);
    }

    /**
     * 发送post请求
     * @param url       请求地址
     * @param body      请求参数（body参数）
     * @param headers   请求头
     * @return
     */
    public static String doPostWithHeaders(String url, Object body, Map<String, String> headers) {
        return doPostWithHeaders(url, body, headers, String.class);
    }

    /**
     * 发送post请求
     * @param url           请求地址
     * @param body          请求参数（body参数）
     * @param headers       请求头
     * @param responseType  返回结果类型
     * @return
     */
    public static <T> T doPostWithHeaders(String url, Object body, Map<String, String> headers, Class<T> responseType) {
        return doPostWithHeaders(url, null, body, headers, responseType);
    }

    /**
     * 发送post请求
     * @param url       请求地址
     * @param params    请求参数（url参数）
     * @param body      请求参数（body参数）
     * @param headers   请求头
     * @return
     */
    public static String doPostWithHeaders(String url, Map<String, String[]> params, Object body, Map<String, String> headers) {
        return doPostWithHeaders(url, params, body, headers, String.class);
    }

    /**
     * 发送post请求
     * @param url           请求地址
     * @param params        请求参数（url参数）
     * @param body          请求参数（body参数）
     * @param headers       请求头
     * @param responseType  返回结果类型
     * @return
     */
    public static <T> T doPostWithHeaders(String url, Map<String, String[]> params, Object body, Map<String, String> headers, Class<T> responseType) {
        if (Objects.isNull(body)) {
            body = new HashMap<>(4);
        }
        HttpHeaders httpHeaders = getHttpHeaders(headers);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity httpEntity = new HttpEntity(body, httpHeaders);
        ResponseEntity<T> responseEntity = restTemplate.exchange(getUrlWithParams(url, params), HttpMethod.POST, httpEntity, responseType);
        return responseEntity.getBody();
    }

    /**
     * 文件上传
     * @param url                   请求地址
     * @param fileMultiValueMap     上传文件信息
     * @return
     */
    public static String upload(String url, MultiValueMap<String, FileUpload> fileMultiValueMap) {
        return upload(url, fileMultiValueMap, String.class);
    }

    /**
     * 文件上传
     * @param url                   请求地址
     * @param fileMultiValueMap     上传文件信息
     * @param responseType          返回结果类型
     * @return
     */
    public static <T> T upload(String url, MultiValueMap<String, FileUpload> fileMultiValueMap, Class<T> responseType) {
        return upload(url, null, fileMultiValueMap, responseType);
    }

    /**
     * 文件上传
     * @param url                   请求地址
     * @param formData              表单信息
     * @param fileMultiValueMap     上传文件信息
     * @return
     */
    public static String upload(String url, MultiValueMap<String, Object> formData, MultiValueMap<String, FileUpload> fileMultiValueMap) {
        return upload(url, formData, fileMultiValueMap, String.class);
    }

    /**
     * 文件上传
     * @param url                   请求地址
     * @param formData              表单信息
     * @param fileMultiValueMap     上传文件信息
     * @param responseType          返回结果类型
     * @return
     */
    public static <T> T upload(String url, MultiValueMap<String, Object> formData, MultiValueMap<String, FileUpload> fileMultiValueMap, Class<T> responseType) {
        return upload(url, null, null, formData, fileMultiValueMap, responseType);
    }

    /**
     * 文件上传
     * @param url                   请求地址
     * @param params                请求参数（url参数）
     * @param headers               请求头
     * @param formData              表单信息
     * @param fileMultiValueMap     上传文件信息
     * @return
     */
    public static String upload(String url, Map<String, String[]> params, Map<String, String> headers, MultiValueMap<String, Object> formData, MultiValueMap<String, FileUpload> fileMultiValueMap) {
        return upload(url, params, headers, formData, fileMultiValueMap, String.class);
    }

    /**
     * 文件上传
     * @param url                   请求地址
     * @param params                请求参数（url参数）
     * @param headers               请求头
     * @param formData              表单信息
     * @param fileMultiValueMap     上传文件信息
     * @param responseType          返回结果类型
     * @return
     */
    public static <T> T upload(String url, Map<String, String[]> params, Map<String, String> headers, MultiValueMap<String, Object> formData, MultiValueMap<String, FileUpload> fileMultiValueMap, Class<T> responseType) {

        HttpHeaders httpHeaders = getHttpHeaders(headers);
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        if (formData == null) {
            formData = new LinkedMultiValueMap<>();
        }
        if (fileMultiValueMap == null) {
            fileMultiValueMap = new LinkedMultiValueMap<>();
        }
        for (Map.Entry<String, List<FileUpload>> entry : fileMultiValueMap.entrySet()) {
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
        ResponseEntity<T> responseEntity = restTemplate.exchange(getUrlWithParams(url, params), HttpMethod.POST, httpEntity, responseType);
        return responseEntity.getBody();
    }

    /**
     * form表单提交
     * @param url       请求地址
     * @param formData  表单信息
     * @return
     */
    public static String form(String url, MultiValueMap<String, Object> formData) {
        return form(url, formData, String.class);
    }

    /**
     * form表单提交
     * @param url           请求地址
     * @param formData      表单信息
     * @param responseType  返回结果类型
     * @return
     */
    public static <T> T form(String url, MultiValueMap<String, Object> formData, Class<T> responseType) {
        return form(url, null, null, formData, responseType);
    }

    /**
     * form表单提交
     * @param url       请求地址
     * @param headers   请求头
     * @param formData  表单信息
     * @return
     */
    public static String form(String url, Map<String, String> headers, MultiValueMap<String, Object> formData) {
        return form(url, headers, formData, String.class);
    }

    /**
     * form表单提交
     * @param url           请求地址
     * @param headers       请求头
     * @param formData      表单信息
     * @param responseType  返回结果类型
     * @return
     */
    public static <T> T form(String url, Map<String, String> headers, MultiValueMap<String, Object> formData, Class<T> responseType) {
        return form(url, null, headers, formData, responseType);
    }

    /**
     * form表单提交
     * @param url       请求地址
     * @param params    请求参数（url参数）
     * @param headers   请求头
     * @param formData  表单信息
     * @return
     */
    public static String form(String url, Map<String, String[]> params, Map<String, String> headers, MultiValueMap<String, Object> formData) {
        return form(url, params, headers, formData, String.class);
    }

    /**
     * form表单提交
     * @param url           请求地址
     * @param params        请求参数（url参数）
     * @param headers       请求头
     * @param formData      表单信息
     * @param responseType  返回结果类型
     * @return
     */
    public static <T> T form(String url, Map<String, String[]> params, Map<String, String> headers, MultiValueMap<String, Object> formData, Class<T> responseType) {
        HttpHeaders httpHeaders = getHttpHeaders(headers);
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        if (formData == null) {
            formData = new LinkedMultiValueMap<>();
        }
        HttpEntity httpEntity = new HttpEntity(formData, httpHeaders);
        ResponseEntity<T> responseEntity = restTemplate.exchange(getUrlWithParams(url, params), HttpMethod.POST, httpEntity, responseType);
        return responseEntity.getBody();
    }

    /**
     * 处理请求头
     * @param headers   请求头
     * @return
     */
    private static HttpHeaders getHttpHeaders(Map<String, String> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if (Objects.nonNull(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpHeaders.add(entry.getKey(), entry.getValue());
            }
        }
        return httpHeaders;
    }

    /**
     * 处理请求参数
     * @param url       请求地址
     * @param params    请求参数（url参数）
     * @return
     */
    private static String getUrlWithParams(String url, Map<String, String[]> params) {
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

}
