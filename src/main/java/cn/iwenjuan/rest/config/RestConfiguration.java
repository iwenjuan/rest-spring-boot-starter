package cn.iwenjuan.rest.config;

import cn.iwenjuan.rest.context.SpringApplicationContext;
import cn.iwenjuan.rest.enums.ClientType;
import cn.iwenjuan.rest.properties.RestProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author li1244
 * @date 2023/4/26 15:08
 */
@Configuration
public class RestConfiguration {

    @Bean(name = "restSpringApplicationContext")
    public SpringApplicationContext springApplicationContext() {
        return new SpringApplicationContext();
    }

    @Bean(name = "restTemplate")
    @ConditionalOnMissingBean(name = "restTemplate")
    public RestTemplate restTemplate() {
        return RestTemplateBuilder.create()
                .clientType(ClientType.URLConnection)
                .urlConnection(new RestProperties.UrlConnectionProperties())
                .build();
    }
}
