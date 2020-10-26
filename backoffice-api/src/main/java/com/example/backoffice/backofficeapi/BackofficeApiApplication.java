package com.example.backoffice.backofficeapi;

import com.example.backoffice.backofficeapi.restservices.User;
import com.example.backoffice.backofficeapi.restservices.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class BackofficeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackofficeApiApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		return args -> {
			Stream.of("John", "Julie", "Jennifer", "Helen", "Rachel").forEach(name -> {
				User user = new User();
				user.setName(name);
				user.setEmail(name.toLowerCase()+"domain.com");
				userRepository.save(user);
			});
			userRepository.findAll().forEach(System.out::println);
		};
	}

}
