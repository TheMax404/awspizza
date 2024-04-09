package it.adesso.awspizza;

import it.adesso.awspizza.utility.ConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ConfigProperties.class)
public class AwspizzaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AwspizzaApplication.class, args);
	}

}
