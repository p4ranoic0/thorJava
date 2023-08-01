package pe.isil.thor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ThorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ThorApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}
}
