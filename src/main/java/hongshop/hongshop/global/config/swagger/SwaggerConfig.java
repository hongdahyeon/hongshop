package hongshop.hongshop.global.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        Info info = new Info()
                .contact(new Contact()
                        .name("hong shop")
                        .email("")
                        .url(""))
                .version("v1.0")
                .title("hong shop API")
                .description("hong shop API");

        return new OpenAPI().info(info);
    }

}
