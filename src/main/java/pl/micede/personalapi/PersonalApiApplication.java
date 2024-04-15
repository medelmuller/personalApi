package pl.micede.personalapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@OpenAPIDefinition(

        info = @Info(
                title = "Aplikacja personalApi",
                version = "1.0.0"
        )
)
@SpringBootApplication
public class PersonalApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalApiApplication.class, args);
    }

}
