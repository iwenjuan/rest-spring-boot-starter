package cn.iwenjuan.rest.config;

import cn.iwenjuan.rest.enums.ClientType;
import cn.iwenjuan.rest.properties.RestProperties;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * @author li1244
 * @date 2023/4/26 16:18
 */
@Slf4j
public class RestTemplateBuilder {

    /**
     * 客户端类型
     */
    private ClientType clientType = ClientType.URLConnection;
    /**
     * URLConnection配置
     */
    private RestProperties.UrlConnectionProperties urlConnection;
    /**
     * HttpClient配置
     */
    private RestProperties.HttpClientProperties httpClient;
    /**
     * OKHttp配置
     */
    private RestProperties.OkHttpProperties okHttp;

    /**
     * 创建RestTemplate构建工具
     * @return
     */
    public static RestTemplateBuilder create() {
        return new RestTemplateBuilder();
    }

    /**
     * 设置客户端类型
     * @param clientType
     * @return
     */
    public RestTemplateBuilder clientType(ClientType clientType) {
        this.clientType = clientType;
        return this;
    }

    /**
     * 设置URLConnection配置
     * @param urlConnection
     * @return
     */
    public RestTemplateBuilder urlConnection(RestProperties.UrlConnectionProperties urlConnection) {
        this.urlConnection = urlConnection;
        return this;
    }

    /**
     * 设置HttpClient配置
     * @param httpClient
     * @return
     */
    public RestTemplateBuilder httpClient(RestProperties.HttpClientProperties httpClient) {
        this.httpClient = httpClient;
        return this;
    }

    /**
     * 设置OKHttp配置
     * @param okHttp
     * @return
     */
    public RestTemplateBuilder okHttp(RestProperties.OkHttpProperties okHttp) {
        this.okHttp = okHttp;
        return this;
    }

    /**
     * 构建RestTemplate
     * @return
     */
    public RestTemplate build() {
        log.info("初始化RestTemplate，客户端类型：{}", clientType);
        RestTemplate restTemplate = null;
        switch (clientType) {
            case URLConnection:
                restTemplate = new RestTemplate(simpleClientHttpRequestFactory());
                break;
            case HttpClient:
                restTemplate = new RestTemplate(httpComponentsClientHttpRequestFactory());
                break;
            case OKHttp:
                restTemplate = new RestTemplate(okHttp3ClientHttpRequestFactory());
                break;
            default:
                throw new RuntimeException("客户端类型异常");
        }
        return restTemplate;
    }

    /**
     * 构建URLConnection连接工厂
     *
     * @return
     */
    private SimpleClientHttpRequestFactory simpleClientHttpRequestFactory() {

        if (urlConnection == null) {
            urlConnection = new RestProperties.UrlConnectionProperties();
        }
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(HttpURLConnection connection, @NotNull String httpMethod) throws IOException {
                super.prepareConnection(connection, httpMethod);
                if (urlConnection.isKeepAlive()) {
                    // 设置长链接
                    connection.setRequestProperty("Connection", "Keep-Alive");
                } else {
                    // 设置短链接
                    connection.setRequestProperty("Connection", "Close");
                }
                if (connection instanceof HttpsURLConnection) {
                    // 设置SSL连接工厂，忽略证书配置
                    ((HttpsURLConnection) connection).setSSLSocketFactory(getSSLSocketFactory());
                }
            }
        };
        // 设置连接超时时间
        factory.setConnectTimeout(urlConnection.getConnectTimeout());
        // 设置响应超时时间
        factory.setReadTimeout(urlConnection.getReadTimeout());
        return factory;
    }

    /**
     * 构建HttpClient连接工厂
     *
     * @return
     */
    private HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory() {

        if (httpClient == null) {
            httpClient = new RestProperties.HttpClientProperties();
        }
        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(getSSLContext(), new NoopHostnameVerifier());

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslConnectionSocketFactory)
                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);

        // 设置连接池最大连接
        connectionManager.setMaxTotal(httpClient.getMaxTotalConnect());
        // 每路由最高并发
        connectionManager.setDefaultMaxPerRoute(httpClient.getMaxConnPerRoute());

        RequestConfig requestConfig = RequestConfig.custom()
                // 从连接池中获取连接的超时时间
                .setConnectionRequestTimeout(httpClient.getConnectionRequestTimout())
                // 连接上服务器的超时时间
                .setConnectionRequestTimeout(httpClient.getConnectTimeout())
                // 返回数据的超时时间
                .setSocketTimeout(httpClient.getSocketTimeout())
                .build();

        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .build();

        return new HttpComponentsClientHttpRequestFactory(closeableHttpClient);
    }

    /**
     * 构建OkHttp3连接工厂
     * @return
     */
    private OkHttp3ClientHttpRequestFactory okHttp3ClientHttpRequestFactory() {

        if (okHttp == null) {
            okHttp = new RestProperties.OkHttpProperties();
        }
        // 连接池
        ConnectionPool connectionPool = new ConnectionPool(okHttp.getMaxIdleConnections(), okHttp.getKeepAliveDurationNs(), TimeUnit.SECONDS);
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(okHttp.isRetryOnConnectionFailure())
                .sslSocketFactory(getSSLSocketFactory(), getX509TrustManager())
                .connectionPool(connectionPool)
                .connectTimeout(okHttp.getConnectTimeout(), TimeUnit.MILLISECONDS)
                .readTimeout(okHttp.getReadTimeout(), TimeUnit.MILLISECONDS)
                .writeTimeout(okHttp.getWriteTimeout(), TimeUnit.MILLISECONDS)
                .build();
        OkHttp3ClientHttpRequestFactory factory = new OkHttp3ClientHttpRequestFactory(client);
        return factory;
    }

    /**
     * 构建SSL连接工厂
     * @return
     */
    private SSLSocketFactory getSSLSocketFactory() {

        return getSSLContext().getSocketFactory();
    }

    /**
     * 构建SSLContext
     * @return
     */
    private SSLContext getSSLContext() {

        SSLContext sslContext;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{getX509TrustManager()}, new SecureRandom());
            sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
        return sslContext;
    }

    /**
     * 构建X509TrustManager
     * @return
     */
    private X509TrustManager getX509TrustManager() {
        return new X509TrustManager() {

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) {
            }
        };
    }
}
