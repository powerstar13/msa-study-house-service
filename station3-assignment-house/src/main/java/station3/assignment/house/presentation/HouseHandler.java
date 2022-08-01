package station3.assignment.house.presentation;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import station3.assignment.house.application.HouseFacade;
import station3.assignment.house.infrastructure.exception.status.BadRequestException;
import station3.assignment.house.infrastructure.exception.status.ExceptionMessage;
import station3.assignment.house.presentation.request.HouseModifyRequest;
import station3.assignment.house.presentation.request.HousePageRequest;
import station3.assignment.house.presentation.request.HouseRegisterRequest;
import station3.assignment.house.presentation.request.HouseRequestMapper;
import station3.assignment.house.presentation.response.*;
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

    /**
     * 내방 삭제
     * @param serverRequest: 삭제할 내방 대체 식별키
     * @return ServerResponse: 처리 완료
     */
    public Mono<ServerResponse> houseDelete(ServerRequest serverRequest) {

        String houseToken = serverRequest.pathVariable("houseToken"); // 방 대체 식별키 추출
        if (StringUtils.isBlank(houseToken)) throw new BadRequestException(ExceptionMessage.IsRequiredHouseToken.getMessage());

        return houseFacade.houseDelete(houseToken)
            .then(
                ok().contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(new SuccessResponse()), SuccessResponse.class)
            );
    }

    /**
     * 내방 정보 조회
     * @param serverRequest: 조회할 방 대체 식별키
     * @return ServerResponse: 방 정보
     */
    public Mono<ServerResponse> houseInfo(ServerRequest serverRequest) {

        String houseToken = serverRequest.pathVariable("houseToken"); // 방 대체 식별키 추출
        if (StringUtils.isBlank(houseToken)) throw new BadRequestException(ExceptionMessage.IsRequiredHouseToken.getMessage());

        Mono<HouseInfoResponse> response = houseFacade.houseInfo(houseToken)
            .flatMap(houseInfo -> Mono.just(houseResponseMapper.of(houseInfo)));

        return ok().contentType(MediaType.APPLICATION_JSON)
            .body(response, HouseInfoResponse.class);
    }

    /**
     * 내방 목록 조회
     * @param serverRequest: 조회할 회원 대체 식별키
     * @return ServerResponse: 방 목록
     */
    public Mono<ServerResponse> houseList(ServerRequest serverRequest) {

        String memberToken = serverRequest.pathVariable("memberToken"); // 회원 대체 식별키 추출
        if (StringUtils.isBlank(memberToken)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberToken.getMessage());

        Mono<HouseListResponse> response = houseFacade.houseList(memberToken)
            .flatMap(houseList -> Mono.just(houseResponseMapper.of(houseList)));

        return ok().contentType(MediaType.APPLICATION_JSON)
            .body(response, HouseListResponse.class);
    }

    /**
     * 전체방 페이지 조회
     * @param serverRequest: 조회할 정보
     * @return ServerResponse: 방 페이지
     */
    public Mono<ServerResponse> housePage(ServerRequest serverRequest) {

        HousePageRequest request = houseRequestMapper.of(serverRequest.queryParams().toSingleValueMap());
        request.verify(); // Request 유효성 검사

        Mono<HousePageResponse> response = houseFacade.housePage(houseRequestMapper.of(request))
            .flatMap(housePage -> Mono.just(houseResponseMapper.of(housePage)));

        return ok().contentType(MediaType.APPLICATION_JSON)
            .body(response, HousePageResponse.class);
    }
}
