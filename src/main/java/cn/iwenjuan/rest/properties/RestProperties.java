package cn.iwenjuan.rest.properties;

import cn.iwenjuan.rest.enums.ClientType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author li1244
 * @date 2023/4/26 15:12
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class RestProperties {

    /**
     * 客户端类型
     */
    private ClientType clientType = ClientType.URLConnection;
    /**
     * URLConnection配置
     */
    private UrlConnectionProperties urlConnection = new UrlConnectionProperties();
    /**
     * HttpClient配置
     */
    private HttpClientProperties httpClient = new HttpClientProperties();
    /**
     * OKHttp配置
     */
    private OkHttpProperties okHttp = new OkHttpProperties();

    @Data
    public static class UrlConnectionProperties {

        /**
         * 客户端和服务器建立连接超时时间，单位：毫秒，默认：2000
         */
        private int connectTimeout = 2000;
        /**
         * 客户端从服务器读取数据包超时时间，单位：毫秒，默认：30000
         */
        private int readTimeout = 30000;
        /**
         * 是否长链接
         */
        private boolean keepAlive = true;
    }

    @Data
    public static class HttpClientProperties {

        /**
         * 连接池的最大连接数，默认值：20
         */
        private int maxTotalConnect = 20;
        /**
         * 相同域名允许创建的最大连接数，默认值：2
         */
        private int maxConnPerRoute = 2;
        /**
         * 客户端和服务器建立连接超时，单位：毫秒，默认值：2000
         */
        private int connectTimeout = 2000;
        /**
         * 读数据的超时时间，单位：毫秒，默认值：30000
         */
        private int socketTimeout = 30000;
        /**
         * 连接池创建连接时的超时时间，不宜过长，单位：毫秒，默认值：200
         */
        private int connectionRequestTimout = 200;
    }

    @Data
    public static class OkHttpProperties {

        /**
         * 失败后是否重试
         */
        private boolean retryOnConnectionFailure = false;
        /**
         * 链接超时时间，单位：毫秒，默认值：10000
         */
        private int connectTimeout = 10000;
        /**
         * 读超时时间，单位：毫秒，默认值：10000
         */
        private int readTimeout = 10000;
        /**
         * 写超时时间，单位：毫秒，默认值：10000
         */
        private int writeTimeout = 10000;
        /**
         * 最大空闲的连接数，默认值：5
         */
        private int maxIdleConnections = 5;
        /**
         * 最大的空闲时间，单位：秒，默认值：3000
         */
        private long keepAliveDurationNs = 3000;
    }
}
