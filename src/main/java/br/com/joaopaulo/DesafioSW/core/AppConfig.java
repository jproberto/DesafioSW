package br.com.joaopaulo.DesafioSW.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe responsável por usar as configurações automáticas do Spring para lançar o projeto
 */

@SpringBootApplication
public class AppConfig {
	public static void main(String[] args) {
		SpringApplication.run(AppConfig.class, args);
	}
}
