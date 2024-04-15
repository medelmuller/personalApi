package pl.micede.personalapi.config.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "admin.info")
@Setter
@Getter
public class AdminConfiguration {

    private String login;
    private String password;

}
