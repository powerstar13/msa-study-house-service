package station3.assignment.house.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import station3.assignment.house.application.HouseFacade;
import station3.assignment.house.infrastructure.exception.status.BadRequestException;
import station3.assignment.house.infrastructure.exception.status.ExceptionMessage;
import station3.assignment.house.presentation.request.HouseModifyRequest;
import station3.assignment.house.presentation.request.HouseRegisterRequest;
import station3.assignment.house.presentation.request.HouseRequestMapper;
import station3.assignment.house.presentation.response.HouseRegisterResponse;
import station3.assignment.house.presentation.response.HouseResponseMapper;
import station3.assignment.house.presentation.shared.response.SuccessResponse;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
@RequiredArgsConstructor
public class HouseHandler {

    private final HouseFacade houseFacade;
    private final HouseRequestMapper houseRequestMapper;
    private final HouseResponseMapper houseResponseMapper;

    /**
     * 내방 등록
     * @param serverRequest: 등록할 내방 정보
     * @return ServerResponse: 내방 대체 식별키 정보
     */
    public Mono<ServerResponse> houseRegister(ServerRequest serverRequest) {

        Mono<HouseRegisterResponse> response = serverRequest.bodyToMono(HouseRegisterRequest.class)
            .switchIfEmpty(Mono.error(new BadRequestException(ExceptionMessage.IsRequiredRequest.getMessage())))
            .flatMap(request -> {
                request.verify(); // Request 유효성 검사

                return houseFacade.houseRegister(houseRequestMapper.of(request));
            })
            .flatMap(houseTokenInfo -> Mono.just(houseResponseMapper.of(houseTokenInfo)));

        return ok().contentType(MediaType.APPLICATION_JSON)
            .body(response, HouseRegisterResponse.class);
    }
    /**
     * 내방 수정
     * @param serverRequest: 수정할 내방 정보
     * @return ServerResponse: 처리 완료
     */
    public Mono<ServerResponse> houseModify(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(HouseModifyRequest.class)
            .switchIfEmpty(Mono.error(new BadRequestException(ExceptionMessage.IsRequiredRequest.getMessage())))
            .flatMap(request -> {
                request.verify(); // Request 유효성 검사

                return houseFacade.houseModify(houseRequestMapper.of(request))
                    .then(
                        ok().contentType(MediaType.APPLICATION_JSON)
                            .body(Mono.just(new SuccessResponse()), SuccessResponse.class)
                    );
            });
    }
}
