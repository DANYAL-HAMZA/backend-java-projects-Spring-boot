package com.example.BankApplication;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "The Danyal Bank Application",
				description = "Backend Rest APIs For Danyal Bank",
				version = "v1.0",
				contact = @Contact(
						name = "DANYAL HAMZA",
						email = "danyalhamzah@gmail.com",
						url = ""
						),
				license = @License(
						name = "DANYAL",
						url = ""
				)

		),
		externalDocs = @ExternalDocumentation(
				description = "The Dnyal Bank Application Description",
				url = ""
		)
)
public class BankApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}

}
