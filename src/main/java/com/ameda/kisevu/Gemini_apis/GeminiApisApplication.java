package com.ameda.kisevu.Gemini_apis;
import com.ameda.kisevu.Gemini_apis.entities.Role;
import com.ameda.kisevu.Gemini_apis.entities.User;
import com.ameda.kisevu.Gemini_apis.repositories.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class GeminiApisApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().load();
		System.setProperty("MYSQL_URL", dotenv.get("MYSQL_URL"));
		System.setProperty("MYSQL_USER", dotenv.get("MYSQL_USER"));
		System.setProperty("MYSQL_PASSWORD", dotenv.get("MYSQL_PASSWORD"));
		System.setProperty("OPEN_API_KEY", dotenv.get("OPEN_API_KEY"));
		System.setProperty("OPEN_API_URL", dotenv.get("OPEN_API_URL"));
		SpringApplication.run(GeminiApisApplication.class, args);
	}

	@Bean
	public Dotenv dotenv(){
		return Dotenv.
				configure()
				.load();
	}


	@Override
	public void run(String... args) throws Exception {
		User adminAccount = userRepository.findByRole(Role.ADMIN);
		if(null == adminAccount){
			User user = User.builder()
					.email("admin@gmail.com")
					.firstname("admin")
					.secondname("admin")
					.role(Role.ADMIN)
					.password(new BCryptPasswordEncoder().encode( "admin"))
					.build();
			userRepository.save(user);
		}
	}
}
