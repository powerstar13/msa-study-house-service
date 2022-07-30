package station3.assignment.house.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import station3.assignment.house.presentation.shared.response.CreatedSuccessResponse;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class HouseHandler {

    /**
     * 내방 등록
     * @param serverRequest: 등록할 내방 정보
     * @return ServerResponse: 내방 대체 식별키 정보
     */
    public Mono<ServerResponse> houseRegister(ServerRequest serverRequest) {

        return ok().contentType(MediaType.APPLICATION_JSON)
            .body(new CreatedSuccessResponse(), CreatedSuccessResponse.class);
    }
}
