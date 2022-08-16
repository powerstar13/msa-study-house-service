package msa.study.member.presentation;

import lombok.RequiredArgsConstructor;
import msa.study.member.application.MemberFacade;
import msa.study.member.infrastructure.exception.status.BadRequestException;
import msa.study.member.infrastructure.exception.status.ExceptionMessage;
import msa.study.member.presentation.request.MemberLoginRequest;
import msa.study.member.presentation.request.MemberRegisterRequest;
import msa.study.member.presentation.request.MemberRequestMapper;
import msa.study.member.presentation.response.MemberResponseMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static msa.study.member.presentation.shared.response.ServerResponseFactory.successBodyValue;

@Component
@RequiredArgsConstructor
public class MemberHandler {

    private final MemberFacade memberFacade;
    private final MemberRequestMapper memberRequestMapper;
    private final MemberResponseMapper memberResponseMapper;

    /**
     * 회원 가입
     * @param serverRequest: 가입할 회원 정보
     * @return MemberRegisterResponse: 회원 토큰 정보
     */
    public Mono<ServerResponse> memberRegister(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(MemberRegisterRequest.class)
            .switchIfEmpty(Mono.error(new BadRequestException(ExceptionMessage.IsRequiredRequest.getMessage())))
            .flatMap(request -> {
                request.verify(); // Request 유효성 검사

                return memberFacade.memberRegister(memberRequestMapper.of(request))
                    .flatMap(response -> successBodyValue(memberResponseMapper.of(response)));
            });
    }

    /**
     * 회원 고유번호 가져오기
     * @param serverRequest: 회원 대체 식별키
     * @return ExchangeMemberTokenResponse: 회원 고유번호
     */
    public Mono<ServerResponse> exchangeMemberToken(ServerRequest serverRequest) {

        String memberToken = serverRequest.pathVariable("memberToken"); // 회원 대체 식별키 추출
        if (StringUtils.isBlank(memberToken)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberToken.getMessage());

        return memberFacade.exchangeMemberToken(memberToken)
            .flatMap(response -> successBodyValue(memberResponseMapper.of(response)));
    }

    /**
     * 회원 로그인
     * @param serverRequest: 로그인 정보
     * @return ServerResponse: 로그인 회원 정보
     */
    public Mono<ServerResponse> memberLogin(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(MemberLoginRequest.class)
            .switchIfEmpty(Mono.error(new BadRequestException(ExceptionMessage.IsRequiredRequest.getMessage())))
            .flatMap(request -> {
                request.verify(); // Request 유효성 검사

                return memberFacade.login(memberRequestMapper.of(request))
                    .flatMap(response -> successBodyValue(memberResponseMapper.of(response)));
            });
    }
}
