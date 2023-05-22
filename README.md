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
参考sample工程中的测试用例，路径：sample/src/test/java/cn.iwenjuan.sample/HttpClientTest
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