package co.unicauca.segundoParcial;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SegundoParcialApplication {

	public static void main(String[] args) {
		SpringApplication.run(SegundoParcialApplication.class, args);
	}


	@Bean
	public OpenAPI custonOpenAPI() {
		return new OpenAPI().info(new Info()
				.title("SOFTWARE DE SEGUIMIENTO DE ACCIONES")
				.version("1.0.0")
				.description("Software que permite realizar un seguimiento de las acciones a los usuarios en una aplicación con Spring Boot."));
	}

}