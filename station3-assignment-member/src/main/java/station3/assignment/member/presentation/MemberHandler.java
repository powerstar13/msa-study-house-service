package station3.assignment.member.presentation;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import station3.assignment.member.application.MemberFacade;
import station3.assignment.member.infrastructure.exception.status.BadRequestException;
import station3.assignment.member.infrastructure.exception.status.ExceptionMessage;
import station3.assignment.member.presentation.request.MemberRegisterRequest;
import station3.assignment.member.presentation.request.MemberRequestMapper;
import station3.assignment.member.presentation.response.ExchangeMemberTokenResponse;
import station3.assignment.member.presentation.response.MemberRegisterResponse;
import station3.assignment.member.presentation.response.MemberResponseMapper;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

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

        Mono<MemberRegisterResponse> response = serverRequest.bodyToMono(MemberRegisterRequest.class)
            .switchIfEmpty(Mono.error(new BadRequestException(ExceptionMessage.IsRequiredRequest.getMessage())))
            .flatMap(request -> {
                request.verify(); // Request 유효성 검사

                return memberFacade.memberRegister(memberRequestMapper.of(request));
            })
            .flatMap(memberTokenInfo -> Mono.just(memberResponseMapper.of(memberTokenInfo)));

        return ok().contentType(MediaType.APPLICATION_JSON)
            .body(response, MemberRegisterResponse.class);
    }

    /**
     * 회원 고유번호 가져오기
     * @param serverRequest: 회원 대체 식별키
     * @return ExchangeMemberTokenResponse: 회원 고유번호
     */
    public Mono<ServerResponse> exchangeMemberToken(ServerRequest serverRequest) {

        String memberToken = serverRequest.pathVariable("memberToken"); // 회원 대체 식별키 추출
        if (StringUtils.isBlank(memberToken)) throw new BadRequestException(ExceptionMessage.IsRequiredMemberToken.getMessage());

        Mono<ExchangeMemberTokenResponse> response = memberFacade.exchangeMemberToken(memberToken)
            .flatMap(memberIdInfo -> Mono.just(memberResponseMapper.of(memberIdInfo)));

        return ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(response, ExchangeMemberTokenResponse.class);
    }
}
