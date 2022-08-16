package msa.study.house.presentation;

import lombok.RequiredArgsConstructor;
import msa.study.house.application.HouseFacade;
import msa.study.house.infrastructure.exception.status.BadRequestException;
import msa.study.house.infrastructure.exception.status.ExceptionMessage;
import msa.study.house.presentation.request.HouseModifyRequest;
import msa.study.house.presentation.request.HousePageRequest;
import msa.study.house.presentation.request.HouseRegisterRequest;
import msa.study.house.presentation.request.HouseRequestMapper;
import msa.study.house.presentation.response.HouseResponseMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static msa.study.house.presentation.shared.response.ServerResponseFactory.successBodyValue;
import static msa.study.house.presentation.shared.response.ServerResponseFactory.successOnly;

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

        return serverRequest.bodyToMono(HouseRegisterRequest.class)
            .switchIfEmpty(Mono.error(new BadRequestException(ExceptionMessage.IsRequiredRequest.getMessage())))
            .flatMap(request -> {
                request.verify(); // Request 유효성 검사

                return houseFacade.houseRegister(houseRequestMapper.of(request))
                    .flatMap(response -> successBodyValue(houseResponseMapper.of(response)));
            });
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
                    .then(successOnly());
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
            .then(successOnly());
    }

    /**
     * 내방 정보 조회
     * @param serverRequest: 조회할 방 대체 식별키
     * @return ServerResponse: 방 정보
     */
    public Mono<ServerResponse> houseInfo(ServerRequest serverRequest) {

        String houseToken = serverRequest.pathVariable("houseToken"); // 방 대체 식별키 추출
        if (StringUtils.isBlank(houseToken)) throw new BadRequestException(ExceptionMessage.IsRequiredHouseToken.getMessage());

        return houseFacade.houseInfo(houseToken)
            .flatMap(response -> successBodyValue(houseResponseMapper.of(response)));
    }

    /**
     * 내방 목록 조회
     * @param serverRequest: 조회할 회원 대체 식별키
     * @return ServerResponse: 방 목록
     */
    public Mono<ServerResponse> houseList(ServerRequest serverRequest) {

        String memberToken = serverRequest.pathVariable("memberToken"); // 회원 대체 식별키 추출
        if (StringUtils.isBlank(memberToken)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberToken.getMessage());

        return houseFacade.houseList(memberToken)
            .flatMap(response -> successBodyValue(houseResponseMapper.of(response)));
    }

    /**
     * 전체방 페이지 조회
     * @param serverRequest: 조회할 정보
     * @return ServerResponse: 방 페이지
     */
    public Mono<ServerResponse> housePage(ServerRequest serverRequest) {

        HousePageRequest request = houseRequestMapper.of(serverRequest.queryParams().toSingleValueMap());
        request.verify(); // Request 유효성 검사

        return houseFacade.housePage(houseRequestMapper.of(request))
            .flatMap(response -> successBodyValue(houseResponseMapper.of(response)));
    }
}
