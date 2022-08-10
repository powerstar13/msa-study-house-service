package msa.study.house.infrastructure.router;

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
import msa.study.house.presentation.HouseHandler;

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
    public RouterFunction<ServerResponse> routerBuilder(HouseHandler houseHandler) {

        return RouterFunctions.route()
            .resources("/**", new ClassPathResource("static/docs")) // API 문서 제공
            .path(RouterPathPattern.HOUSE_ROOT.getPath(), builder1 ->
                builder1.nest(accept(MediaType.APPLICATION_JSON), builder2 ->
                    builder2
                        .POST(RouterPathPattern.HOUSE_REGISTER.getPath(), houseHandler::houseRegister) // 내방 등록
                        .PUT(RouterPathPattern.HOUSE_MODIFY.getPath(), houseHandler::houseModify) // 내방 수정
                        .DELETE(RouterPathPattern.HOUSE_DELETE.getPath(), houseHandler::houseDelete) // 내방 삭제
                )
            )
            .path(RouterPathPattern.HOUSE_ROOT.getPath(), builder ->
                builder
                    .GET(RouterPathPattern.HOUSE_INFO.getPath(), houseHandler::houseInfo) // 내방 정보 조회
                    .GET(RouterPathPattern.HOUSE_LIST.getPath(), houseHandler::houseList) // 내방 목록 조회
                    .GET(RouterPathPattern.HOUSE_PAGE.getPath(), houseHandler::housePage) // 전체방 페이지 조회
            )
            .build();
    }
}
