# rest-spring-boot-starter

## 介绍
1. Http请求工具，简化http请求的开发，轻松实现http请求（包括文件上传、下载）
2. 支持URLConnection、HttpClient、OKHttp
3. 支持自定义RestTemplate，只需要把自定义的RestTemplate注入spring容器
4. 目前只测试了SpringBoot 2.7.X版本

## 使用说明

### maven引入依赖
~~~
<dependency>
    <groupId>cn.iwenjuan</groupId>
    <artifactId>rest-spring-boot-starter</artifactId>
    <version>1.0.1-SNAPSHOT</version>
</dependency>
~~~
### 发送http请求
~~~
package cn.iwenjuan.sample;

import cn.iwenjuan.rest.UploadFile;
import cn.iwenjuan.rest.client.HttpClientBuilder;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author li1244
 * @date 2023/4/26 15:51
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpClientTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void run1() {
        String name = "张三";
        Map<String, String[]> params = new HashMap<>(16);
        params.put("name", new String[]{name, "李四"});
        params.put("date", new String[]{"2023-04-27 10:00:00"});
        String result = testRestTemplate.getForObject("/test?name={name}&date={date}", String.class, params);
        System.out.println("接口返回结果：" + result);
    }

    @Test
    public void run() {
        String url = "http://127.0.0.1:8080/test";

        String name = "张三";
        Map<String, String[]> params = new HashMap<>(16);
        params.put("name", new String[]{name, "李四"});
        params.put("date", new String[]{"2023-04-27 10:00:00"});

        // 测试GET请求
        String getResult = HttpClientBuilder.create(url).params(params).build().doGet();
        System.out.println("get请求：" + getResult);

        // 测试POST请求
        String postResult = HttpClientBuilder.create(url).body(params).build().doPost();
        System.out.println("post请求：" + postResult);

        // 测试PUT请求
        String putResult = HttpClientBuilder.create(url).body(params).build().doPut();
        System.out.println("put请求：" + putResult);

        // 测试DELETE请求
        String deleteResult = HttpClientBuilder.create(url).body(params).build().doDelete();
        System.out.println("delete请求：" + deleteResult);
    }

    @Test
    public void download() throws IOException {
        // 测试文件下载
        String fileUrl = "https://img0.baidu.com/it/u=741268616,1401664941&fm=253&fmt=auto&app=138&f=JPEG?w=748&h=500";
        byte[] bytes = HttpClientBuilder.create(fileUrl).build().doGet(byte[].class);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        FileOutputStream outputStream = new FileOutputStream("C://tmp/968e0ad51b9b2130f6373e4b5fc14052.jpeg");
        IOUtils.copy(inputStream, outputStream);
    }

    @Test
    public void upload() throws IOException {
        // 测试文件上传
        String url = "http://127.0.0.1:8080/storage/upload";

        File file = new File("C://tmp/968e0ad51b9b2130f6373e4b5fc14052.jpeg");
        MultiValueMap<String, UploadFile> fileMap = new LinkedMultiValueMap<>();
        fileMap.add("file", new UploadFile(file));
        String upload = HttpClientBuilder.create(url).fileMap(fileMap).build().doUpload();
        System.out.println("文件上传：" + upload);
    }

}
~~~
### 自定义RestTemplate，参考sample工程中的RestTemplateConfig
~~~
@Bean
@ConfigurationProperties(prefix = "spring.rest")
public RestProperties restProperties() {
    return new RestProperties();
}

@Bean(name = "restTemplate")
public RestTemplate restTemplate(RestProperties restProperties) {
    return RestTemplateBuilder.create()
            .clientType(restProperties.getClientType())
            .urlConnection(restProperties.getUrlConnection())
            .httpClient(restProperties.getHttpClient())
            .okHttp(restProperties.getOkHttp())
            .build();
}
~~~
application.yml配置示例
~~~
spring:
  # http请求配置
  rest:
    # 客户端类型
    client-type: urlconnection
    # URLConnection配置
    url-connection:
      # 客户端和服务器建立连接超时时间
      connect-timeout: 2000
      # 客户端从服务器读取数据包超时时间
      read-timeout: 30000
      # 是否长链接
      keep-alive: false
    # HttpClient配置
    http-client:
      # 连接池的最大连接数
      max-total-connect: 200
      # 相同域名允许创建的最大连接数
      max-connect-per-route: 5
      # 客户端和服务器建立连接超时时间
      connect-timeout: 2000
      # 读数据的超时时间
      socket-timeout: 30000
      # 连接池创建连接时的超时时间
      connection-request-timout: 200
    # OKHttp配置
    ok-http:
      # 失败后是否重试
      retry-on-connection-failure: false
      # 链接超时时间
      connect-timeout: 2000
      # 读超时时间
      read-timeout: 10000
      # 写超时时间
      write-timeout: 10000
      # 最大空闲的连接数
      max-idle-connections: 5
      # 最大的空闲时间
      keep-alive-duration-ns: 3000
~~~