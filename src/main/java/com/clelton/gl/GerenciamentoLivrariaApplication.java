package com.clelton.gl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class GerenciamentoLivrariaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GerenciamentoLivrariaApplication.class, args);
	}

}
