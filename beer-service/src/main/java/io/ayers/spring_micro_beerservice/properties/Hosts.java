package io.ayers.spring_micro_beerservice.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "io.ayers", ignoreUnknownFields = false)
public class Hosts {
    private String beerInventoryServiceHost;
}
