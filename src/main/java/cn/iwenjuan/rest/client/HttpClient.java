package cn.iwenjuan.rest.client;

import cn.iwenjuan.rest.config.RestTemplateBuilder;
import cn.iwenjuan.rest.context.SpringApplicationContext;
import cn.iwenjuan.rest.enums.ClientType;
import cn.iwenjuan.rest.properties.UrlConnectionProperties;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
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
    public static String get(String url) {
        return get(url, String.class);
    }

    /**
     * 发送get请求
     * @param url           请求地址
     * @param responseType  返回结果类型
     * @return
     */
    public static <T> T get(String url, Class<T> responseType) {
        return get(url, null, responseType);
    }

    /**
     * 发送get请求
     * @param url       请求地址
     * @param params    请求参数
     * @return
     */
    public static String get(String url, Map<String, String> params) {
        return get(url, params, String.class);
    }

    /**
     * 发送get请求
     * @param url           请求地址
     * @param params        请求参数
     * @param responseType  返回结果类型
     * @return
     */
    public static <T> T get(String url, Map<String, String> params, Class<T> responseType) {
        return getWithHeaders(url, params, null, responseType);
    }

    /**
     * 发送get请求
     * @param url       请求地址
     * @param headers   请求头
     * @return
     */
    public static String getWithHeaders(String url, Map<String, String> headers) {
        return getWithHeaders(url, headers, String.class);
    }

    /**
     * 发送get请求
     * @param url           请求地址
     * @param headers       请求头
     * @param responseType  返回结果类型
     * @return
     */
    public static <T> T getWithHeaders(String url, Map<String, String> headers, Class<T> responseType) {
        return getWithHeaders(url, null, headers, responseType);
    }

    /**
     * 发送get请求
     * @param url       请求地址
     * @param params    请求参数
     * @param headers   请求头
     * @return
     */
    public static String getWithHeaders(String url, Map<String, String> params, Map<String, String> headers) {
        return getWithHeaders(url, params, headers, String.class);
    }

    /**
     * 发送get请求
     * @param url           请求地址
     * @param params        请求参数
     * @param headers       请求头
     * @param responseType  返回结果类型
     * @return
     */
    public static <T> T getWithHeaders(String url, Map<String, String> params, Map<String, String> headers, Class<T> responseType) {

        HttpHeaders httpHeaders = new HttpHeaders();
        if (Objects.nonNull(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpHeaders.add(entry.getKey(), entry.getValue());
            }
        }
        HttpEntity httpEntity = new HttpEntity(null, httpHeaders);
        ResponseEntity<T> responseEntity = restTemplate.exchange(getUrlWithParams(url, params), HttpMethod.GET, httpEntity, responseType);
        return responseEntity.getBody();
    }

    /**
     * 发送post请求
     * @param url   请求地址
     * @return
     */
    public static String post(String url) {
        return post(url, String.class);
    }

    /**
     * 发送post请求
     * @param url           请求地址
     * @param responseType  返回结果类型
     * @return
     */
    public static <T> T post(String url, Class<T> responseType) {
        return post(url, null, null, responseType);
    }

    /**
     * 发送post请求
     * @param url       请求地址
     * @param body      请求参数（body参数）
     * @return
     */
    public static String post(String url, Object body) {
        return post(url, body, String.class);
    }

    /**
     * 发送post请求
     * @param url           请求地址
     * @param body          请求参数（body参数）
     * @param responseType  返回结果类型
     * @return
     */
    public static <T> T post(String url, Object body, Class<T> responseType) {
        return post(url, null, body, responseType);
    }

    /**
     * 发送post请求
     * @param url       请求地址
     * @param params    请求参数（url参数）
     * @param body      请求参数（body参数）
     * @return
     */
    public static String post(String url, Map<String, String> params, Object body) {
        return post(url, params, body, String.class);
    }

    /**
     * 发送post请求
     * @param url           请求地址
     * @param params        请求参数（url参数）
     * @param body          请求参数（body参数）
     * @param responseType  返回结果类型
     * @return
     */
    public static <T> T post(String url, Map<String, String> params, Object body, Class<T> responseType) {
        return postWithHeaders(url, params, body, null, responseType);
    }

    /**
     * 发送post请求
     * @param url       请求地址
     * @param body      请求参数（body参数）
     * @param headers   请求头
     * @return
     */
    public static String postWithHeaders(String url, Object body, Map<String, String> headers) {
        return postWithHeaders(url, body, headers, String.class);
    }

    /**
     * 发送post请求
     * @param url           请求地址
     * @param body          请求参数（body参数）
     * @param headers       请求头
     * @param responseType  返回结果类型
     * @return
     */
    public static <T> T postWithHeaders(String url, Object body, Map<String, String> headers, Class<T> responseType) {
        return postWithHeaders(url, null, body, headers, responseType);
    }

    /**
     * 发送post请求
     * @param url       请求地址
     * @param params    请求参数（url参数）
     * @param body      请求参数（body参数）
     * @param headers   请求头
     * @return
     */
    public static String postWithHeaders(String url, Map<String, String> params, Object body, Map<String, String> headers) {
        return postWithHeaders(url, params, body, headers, String.class);
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
    public static <T> T postWithHeaders(String url, Map<String, String> params, Object body, Map<String, String> headers, Class<T> responseType) {
        if (Objects.isNull(body)) {
            body = new HashMap<>(4);
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        if (Objects.nonNull(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                httpHeaders.add(entry.getKey(), entry.getValue());
            }
        }
        HttpEntity httpEntity = new HttpEntity(body, httpHeaders);
        ResponseEntity<T> responseEntity = restTemplate.exchange(getUrlWithParams(url, params), HttpMethod.POST, httpEntity, responseType);
        return responseEntity.getBody();
    }

    /**
     * 处理请求参数
     * @param url       请求地址
     * @param params    请求参数（url参数）
     * @return
     */
    private static String getUrlWithParams(String url, Map<String, String> params) {
        StringBuffer buffer = new StringBuffer();
        if (Objects.nonNull(params)) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String value = entry.getValue();
                try {
                    value = URLEncoder.encode(value, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                buffer.append(entry.getKey()).append("=").append(value).append("&");
            }
        }
        if (buffer.length() > 0) {
            String paramsStr = buffer.toString();
            url = url.concat("?").concat(paramsStr);
        }
        return url;
    }

}
