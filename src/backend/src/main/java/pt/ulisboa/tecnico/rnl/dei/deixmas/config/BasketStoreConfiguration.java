package pt.ulisboa.tecnico.rnl.dei.deixmas.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "api.basketstore")
public class BasketStoreConfiguration {
	private String url;
	private String key;
}
