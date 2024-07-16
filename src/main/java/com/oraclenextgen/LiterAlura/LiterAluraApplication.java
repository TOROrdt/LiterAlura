package com.oraclenextgen.LiterAlura;

import com.oraclenextgen.LiterAlura.main.Main;
import com.oraclenextgen.LiterAlura.repository.AutorRepository;
import com.oraclenextgen.LiterAlura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private AutorRepository autorRepository;

	public static void main(String[] args) {

		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Main menu = new Main(libroRepository, autorRepository);
		menu.mostrarMenu();

    }
}
