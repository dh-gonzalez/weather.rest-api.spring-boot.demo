package weather.rest_api.spring_boot.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClient configuration
 */
@Configuration
public class WebClientConfig {

    @Value("${spring.weatherbit.url:http://api.weatherbit.io}")
    private String weatherbitUrl;

    @Bean
    public WebClient weatherbitWebClient(WebClient.Builder builder) {
        return builder.baseUrl(weatherbitUrl).build();
    }
}
