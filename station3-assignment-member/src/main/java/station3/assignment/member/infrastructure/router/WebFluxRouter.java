package station3.assignment.member.infrastructure.router;

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
import station3.assignment.member.presentation.MemberHandler;

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
    public RouterFunction<ServerResponse> memberRouterBuilder(MemberHandler memberHandler) {

        return RouterFunctions.route()
            .resources("/**", new ClassPathResource("static/docs")) // API 문서 제공
            .path(RouterPathPattern.MEMBER_REGISTER.getPath1(), memberBuilder ->
                memberBuilder.nest(accept(MediaType.APPLICATION_JSON), builder ->
                    builder
                        .POST(RouterPathPattern.MEMBER_REGISTER.getPath2(), memberHandler::memberRegister) // 회원 가입
                )
            )
            .path(RouterPathPattern.EXCHANGE_MEMBER_TOKEN.getPath1(), memberBuilder ->
                memberBuilder
                    .GET(RouterPathPattern.EXCHANGE_MEMBER_TOKEN.getPath2(), memberHandler::exchangeMemberToken) // 회원 고유번호 가져오기
            )
            .build();
    }
}
