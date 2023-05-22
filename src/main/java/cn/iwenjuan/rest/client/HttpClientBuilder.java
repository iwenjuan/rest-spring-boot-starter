package cn.iwenjuan.rest.client;

import cn.iwenjuan.rest.UploadFile;
import cn.iwenjuan.rest.RestRequestEntity;
import cn.iwenjuan.rest.config.RestTemplateBuilder;
import cn.iwenjuan.rest.context.SpringApplicationContext;
import cn.iwenjuan.rest.enums.ClientType;
import cn.iwenjuan.rest.properties.RestProperties;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author li1244
 * @date 2023/4/27 10:56
 */
public class HttpClientBuilder {

    private RestTemplate restTemplate;
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

    /**
     * 创建HttpClient构建工具
     * @param url
     * @return
     */
    public static HttpClientBuilder create(String url) {
        return new HttpClientBuilder().url(url);
    }

    /**
     * 设置rest客户端
     * @param restTemplate
     * @return
     */
    public HttpClientBuilder restTemplate(RestTemplate restTemplate) {
        if (restTemplate == null) {
            restTemplate = SpringApplicationContext.getBean("restTemplate");
        }
        if (restTemplate == null) {
            restTemplate = RestTemplateBuilder.create()
                    .clientType(ClientType.URLConnection)
                    .urlConnection(new RestProperties.UrlConnectionProperties())
                    .build();
        }
        this.restTemplate = restTemplate;
        return this;
    }

    /**
     * 设置请求地址
     * @param url
     * @return
     */
    public HttpClientBuilder url(String url) {
        this.url = url;
        return this;
    }

    /**
     * 设置请求参数（url参数）
     * @param params
     * @return
     */
    public HttpClientBuilder params(Map<String, String[]> params) {
        this.params = params;
        return this;
    }

    /**
     * 设置请求参数
     * @param headers
     * @return
     */
    public HttpClientBuilder headers(Map<String, String> headers) {
        this.headers = headers;
        return this;
    }

    /**
     * 设置请求body
     * @param body
     * @return
     */
    public HttpClientBuilder body(Object body) {
        this.body = body;
        return this;
    }

    /**
     * 设置表单数据
     * @param formData
     * @return
     */
    public HttpClientBuilder formData(MultiValueMap<String, Object> formData) {
        this.formData = formData;
        return this;
    }

    /**
     * 设置文件上传信息
     * @param fileMap
     * @return
     */
    public HttpClientBuilder fileMap(MultiValueMap<String, UploadFile> fileMap) {
        this.fileMap = fileMap;
        return this;
    }

    /**
     * 构建HttpClient
     * @return
     */
    public HttpClient build() {
        if (this.restTemplate == null) {
            this.restTemplate(null);
        }
        RestRequestEntity requestEntity = new RestRequestEntity()
                .setUrl(this.url)
                .setParams(this.params)
                .setHeaders(this.headers)
                .setBody(this.body)
                .setFormData(this.formData)
                .setFileMap(this.fileMap);
        return new HttpClient(this.restTemplate, requestEntity);
    }
}
