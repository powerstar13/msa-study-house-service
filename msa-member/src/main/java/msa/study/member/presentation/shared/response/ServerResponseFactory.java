package msa.study.member.presentation.shared.response;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

public class ServerResponseFactory {

    /**
     * API 성공 반환
     */
    public static Mono<ServerResponse> successOnly() {
        return successBodyValue(new SuccessResponse());
    }

    /**
     * 생성 성공 반환
     */
    public static Mono<ServerResponse> createdSuccessOnly() {
        return successBodyValue(new CreatedSuccessResponse());
    }
    
    /**
     * Success response with body (shortcut)
     * @param response : 반환할 값
     */
    public static Mono<ServerResponse> successBodyValue(Object response) {
        
        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(response);
    }
}
