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
public class UrlConnectionProperties {

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
