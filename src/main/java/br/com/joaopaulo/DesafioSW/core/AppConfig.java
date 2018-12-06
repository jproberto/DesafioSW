package br.com.joaopaulo.DesafioSW.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

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
}
