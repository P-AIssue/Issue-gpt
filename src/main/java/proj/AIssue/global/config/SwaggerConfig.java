package proj.AIssue.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI OpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .version("v1.0.0")                     // 버전 기록
                        .title("P-project API")                // API 명세서 제목
                        .description("P-project API Specification"));   // 상세

    }
}
