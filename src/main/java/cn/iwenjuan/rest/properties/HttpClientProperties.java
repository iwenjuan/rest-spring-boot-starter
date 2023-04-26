package cn.iwenjuan.rest.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author li1244
 * @date 2023/4/26 15:13
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class HttpClientProperties {

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
