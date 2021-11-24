package name.lenmar.publications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@SpringBootApplication
public class PublicationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(PublicationsApplication.class, args);
	}
}
