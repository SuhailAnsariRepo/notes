package com.suhail.notes;

import com.suhail.notes.model.Role;
import com.suhail.notes.model.User;
import com.suhail.notes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class NotesApplication implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(NotesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setNumber("9910921083");
		user.setPassword(passwordEncoder.encode("Suhail@123"));
		user.setRole(Role.ADMIN);
		try {
			userRepository.save(user);
		} catch (Exception e) {
			System.out.println("User already exists");
		}
	}
}

