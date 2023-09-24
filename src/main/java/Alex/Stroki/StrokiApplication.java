package Alex.Stroki;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class StrokiApplication {

	public static String authorizeUser;
	public static String notAuthorized = "ОШИБКА!!! \nВы не авторизованы в системе.";
	public static String notFoundOrNorAccess(String shortUrl) { return "ОШИБКА!!! \nСсылка \"" + shortUrl +"\" не найдена в базе данных или не является вашей."; }

	public static void main(String[] args) {
		SpringApplication.run(StrokiApplication.class, args);
	}

	@Bean
	public Docket swagger() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage(this.getClass().getPackageName()))
				.paths(PathSelectors.any())
				.build();
	}

}
