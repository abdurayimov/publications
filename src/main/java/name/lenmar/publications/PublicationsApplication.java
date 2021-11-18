package name.lenmar.publications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@SpringBootApplication
public class PublicationsApplication {

	@Bean
	public LocalValidatorFactoryBean validator(MessageSource messageSource) {
		LocalValidatorFactoryBean validatorFactoryBean = new LocalValidatorFactoryBean();
		validatorFactoryBean.setValidationMessageSource(messageSource);
		return validatorFactoryBean;
	}

	public static void main(String[] args) {
		SpringApplication.run(PublicationsApplication.class, args);
	}

}
