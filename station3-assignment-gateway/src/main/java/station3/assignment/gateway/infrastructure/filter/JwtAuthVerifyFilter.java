package station3.assignment.gateway.infrastructure.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import station3.assignment.gateway.infrastructure.exception.status.ExceptionMessage;
import station3.assignment.gateway.infrastructure.exception.status.UnauthorizedException;
import station3.assignment.gateway.infrastructure.jwt.JwtUtil;

// 사용자 정의 필터를 만들기 위해 Spring Cloud Gateway가 추상화해 놓은 AbstractGatewayFilterFactory를 상속받는다.
@Slf4j
@Component
public class JwtAuthVerifyFilter extends AbstractGatewayFilterFactory<JwtAuthVerifyFilter.Config> {

    private final JwtUtil jwtUtil;

    public JwtAuthVerifyFilter(JwtUtil jwtUtil) {
        super(Config.class);
        this.jwtUtil = jwtUtil;
    }

    // 토큰 검증을 할 로직을 apply 메서드에 추가하면 된다.
    @Override
    public GatewayFilter apply(Config config) {

        // apply 메서드 안에서 첫 번째 파라미터는 ServerWebExchange 형태고, 두 번째 파라미터가 GatewayFilterChain 람다 함수이다.
        return ((exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest(); // Request로 받아오면 Pre Filter가 적용된다. (주의 : reactive.ServerHttpRequest 여야 한다.)

            // Request Header에 token이 존재하지 않을 경우
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new UnauthorizedException(ExceptionMessage.IsRequiredToken.getMessage());
            }

            // Request Header에서 token 추출
            String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            // 토큰 검증
            if (jwtUtil.validateToken(token, true)) {
                return chain.filter(exchange).then(Mono.fromRunnable(() -> log.info("===== 토큰 검증 완료 =====")));
            } else {
                throw new UnauthorizedException(ExceptionMessage.VerifyFailToken.getMessage());
            }
        });
    }

    // 기본 Config class
    public static class Config {}
}
