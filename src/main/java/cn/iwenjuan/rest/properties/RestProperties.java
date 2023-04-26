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
}
