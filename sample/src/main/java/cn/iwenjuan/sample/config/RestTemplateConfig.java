package cn.iwenjuan.sample.config;

import cn.iwenjuan.rest.config.RestTemplateBuilder;
import cn.iwenjuan.rest.properties.RestProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author li1244
 * @date 2023/4/26 16:15
 */
@Configuration
public class RestTemplateConfig {

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
}
