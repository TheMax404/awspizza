package it.adesso.awspizza.utility;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@Data
@Component
public class ConfigProperties {

    private String url;
    private String driverClassName;
    private String username;
    private String password;
}