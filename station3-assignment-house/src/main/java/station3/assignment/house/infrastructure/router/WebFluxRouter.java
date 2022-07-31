package station3.assignment.house.infrastructure.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import station3.assignment.house.presentation.HouseHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
@EnableWebFlux
public class WebFluxRouter implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .maxAge(3600);
    }

    @Bean
    public RouterFunction<ServerResponse> houseRouterBuilder(HouseHandler houseHandler) {

        return RouterFunctions.route()
            .resources("/**", new ClassPathResource("static/docs")) // API 문서 제공
            .path(RouterPathPattern.HOUSE_ROOT.getPath(), memberBuilder ->
                memberBuilder.nest(accept(MediaType.APPLICATION_JSON), builder ->
                    builder
                        .POST(RouterPathPattern.HOUSE_REGISTER.getPath(), houseHandler::houseRegister) // 내방 등록
                        .PUT(RouterPathPattern.HOUSE_MODIFY.getPath(), houseHandler::houseModify) // 내방 수정
                        .DELETE(RouterPathPattern.HOUSE_DELETE.getPath(), houseHandler::houseDelete) // 내방 삭제
                )
            )
            .path(RouterPathPattern.HOUSE_ROOT.getPath(), memberBuilder ->
                memberBuilder
                    .GET(RouterPathPattern.HOUSE_INFO.getPath(), houseHandler::houseInfo) // 내방 정보 조회
            )
            .build();
    }
}
