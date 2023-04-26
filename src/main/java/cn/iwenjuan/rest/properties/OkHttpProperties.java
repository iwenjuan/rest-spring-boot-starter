package cn.iwenjuan.rest.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author li1244
 * @date 2023/4/26 15:14
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class OkHttpProperties {

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
