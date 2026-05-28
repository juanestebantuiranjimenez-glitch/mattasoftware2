package com.example.demo;

import com.example.demo.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// El REPOSITORY
	@Bean
	public CommandLineRunner testRepository(UsuarioRepository repo) {
		return args -> {
			System.out.println("--- PROBANDO CONEXIÓN Y REPOSITORY ---");

			repo.findAll().forEach(user -> {
				System.out.println("Usuario encontrado: " + user.getNombre() + " (" + user.getCorreo() + ")");
			});

			System.out.println("--- PRUEBA TERMINADA CON ÉXITO ---");
		};
	}
}