package br.com.joaopaulo.DesafioSW.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Classe responsável por usar as configurações automáticas do Spring para lançar o projeto
 */

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class AppConfig {
	public static void main(String[] args) {
		SpringApplication.run(AppConfig.class, args);
	}

	@Bean
	public ValidatingMongoEventListener validatingMongoEventListener() {
		return new ValidatingMongoEventListener(validator());
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}
}
