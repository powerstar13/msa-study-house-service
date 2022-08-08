package station3.assignment.member.presentation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;
import station3.assignment.member.application.MemberFacade;
import station3.assignment.member.application.dto.MemberCommand;
import station3.assignment.member.domain.service.dto.MemberDTO;
import station3.assignment.member.infrastructure.router.RouterPathPattern;
import station3.assignment.member.presentation.request.MemberLoginRequest;
import station3.assignment.member.presentation.request.MemberRegisterRequest;
import station3.assignment.member.presentation.request.MemberRequestMapper;
import station3.assignment.member.presentation.response.ExchangeMemberTokenResponse;
import station3.assignment.member.presentation.response.MemberLoginResponse;
import station3.assignment.member.presentation.response.MemberRegisterResponse;
import station3.assignment.member.presentation.response.MemberResponseMapper;
import station3.assignment.member.presentation.shared.WebFluxSharedHandlerTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static station3.assignment.member.infrastructure.factory.MemberTestFactory.*;
import static station3.assignment.member.infrastructure.restdocs.RestdocsDocumentFormat.*;
import static station3.assignment.member.infrastructure.restdocs.RestdocsDocumentUtil.requestPrettyPrint;
import static station3.assignment.member.infrastructure.restdocs.RestdocsDocumentUtil.responsePrettyPrint;

@WebFluxTest(MemberHandler.class)
class MemberHandlerTest extends WebFluxSharedHandlerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private MemberFacade memberFacade;
    @MockBean
    private MemberRequestMapper memberRequestMapper;
    @MockBean
    private MemberResponseMapper memberResponseMapper;

    @DisplayName("회원 가입")
    @Test
    void memberRegister() {
        // given
        given(memberRequestMapper.of(any(MemberRegisterRequest.class))).willReturn(memberRegisterCommand());
        given(memberFacade.memberRegister(any(MemberCommand.MemberRegister.class))).willReturn(memberTokenInfoMono());
        given(memberResponseMapper.of(any(MemberDTO.MemberTokenInfo.class))).willReturn(memberRegisterResponse());

        // when
        final String URI = RouterPathPattern.AUTH_MEMBER_REGISTER.getFullPath();
        WebTestClient.ResponseSpec result = webClient
            .post()
            .uri(URI)
            .bodyValue(memberRegisterRequest())
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

        result.expectStatus().isOk()
            .expectBody()
            .consumeWith(document(URI,
                requestPrettyPrint(),
                responsePrettyPrint(),
                requestFields(
                    fieldWithPath("memberType").type(JsonFieldType.STRING).description("회원 유형").attributes(memberTypeFormat()),
                    fieldWithPath("memberLoginId").type(JsonFieldType.STRING).description("회원 로그인 아이디"),
                    fieldWithPath("memberPassword").type(JsonFieldType.STRING).description("회원 비밀번호"),
                    fieldWithPath("memberName").type(JsonFieldType.STRING).description("회원 이름"),
                    fieldWithPath("memberBirth").type(JsonFieldType.STRING).description("회원 생년월일").attributes(birthFormat()),
                    fieldWithPath("memberPhone").type(JsonFieldType.STRING).description("회원 생년월일").attributes(phoneFormat())
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("결과 코드"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("결과 메시지"),
                    fieldWithPath("memberToken").type(JsonFieldType.STRING).description("회원 대체 식별키"),
                    fieldWithPath("accessToken").type(JsonFieldType.STRING).description("JWT 액세스 토큰"),
                    fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("JWT 리프레시 토큰")
                )
            ));

        FluxExchangeResult<MemberRegisterResponse> flux = result.returnResult(MemberRegisterResponse.class);

        // then
        verify(memberRequestMapper).of(any(MemberRegisterRequest.class));
        verify(memberFacade).memberRegister(any(MemberCommand.MemberRegister.class));
        verify(memberResponseMapper).of(any(MemberDTO.MemberTokenInfo.class));

        StepVerifier.create(flux.getResponseBody().log())
            .assertNext(response -> assertAll(() -> {
                assertEquals(HttpStatus.CREATED.value(), response.getRt());
                assertInstanceOf(String.class, response.getMemberToken());
            }))
            .verifyComplete();
    }

    @DisplayName("회원 고유번호 가져오기")
    @Test
    void exchangeMemberToken() {
        // given
        given(memberFacade.exchangeMemberToken(any(String.class))).willReturn(memberIdInfoMono());
        given(memberResponseMapper.of(any(MemberDTO.MemberIdInfo.class))).willReturn(exchangeMemberTokenResponse());

        // when
        final String URI = RouterPathPattern.EXCHANGE_MEMBER_TOKEN.getFullPath();
        WebTestClient.ResponseSpec result = webClient
            .get()
            .uri(URI, UUID.randomUUID().toString())
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

        result.expectStatus().isOk()
            .expectBody()
            .consumeWith(document(URI,
                requestPrettyPrint(),
                responsePrettyPrint(),
                pathParameters(
                    parameterWithName("memberToken").description("회원 대체 식별키")
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("결과 코드"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("결과 메시지"),
                    fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("회원 고유번호")
                )
            ));

        FluxExchangeResult<ExchangeMemberTokenResponse> flux = result.returnResult(ExchangeMemberTokenResponse.class);

        // then
        verify(memberFacade).exchangeMemberToken(any(String.class));
        verify(memberResponseMapper).of(any(MemberDTO.MemberIdInfo.class));

        StepVerifier.create(flux.getResponseBody().log())
            .assertNext(response -> assertAll(() -> {
                assertEquals(HttpStatus.OK.value(), response.getRt());
                assertTrue(response.getMemberId() > 0);
            }))
            .verifyComplete();
    }

    @DisplayName("회원 로그인")
    @Test
    void memberLogin() {
        // given
        given(memberRequestMapper.of(any(MemberLoginRequest.class))).willReturn(memberLoginCommand());
        given(memberFacade.login(any(MemberCommand.MemberLogin.class))).willReturn(memberLoginInfoResponse());
        given(memberResponseMapper.of(any(MemberDTO.MemberLoginInfo.class))).willReturn(memberLoginResponse());

        // when
        final String URI = RouterPathPattern.AUTH_MEMBER_LOGIN.getFullPath();
        WebTestClient.ResponseSpec result = webClient
            .post()
            .uri(URI)
            .bodyValue(memberLoginRequest())
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

        result.expectStatus().isOk()
            .expectBody()
            .consumeWith(document(URI,
                requestPrettyPrint(),
                responsePrettyPrint(),
                requestFields(
                    fieldWithPath("memberLoginId").type(JsonFieldType.STRING).description("회원 로그인 아이디"),
                    fieldWithPath("memberPassword").type(JsonFieldType.STRING).description("회원 비밀번호")
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("결과 코드"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("결과 메시지"),
                    fieldWithPath("memberName").type(JsonFieldType.STRING).description("회원 이름"),
                    fieldWithPath("memberToken").type(JsonFieldType.STRING).description("회원 대체 식별키"),
                    fieldWithPath("accessToken").type(JsonFieldType.STRING).description("JWT 액세스 토큰"),
                    fieldWithPath("refreshToken").type(JsonFieldType.STRING).description("JWT 리프레시 토큰")
                )
            ));

        FluxExchangeResult<MemberLoginResponse> flux = result.returnResult(MemberLoginResponse.class);

        // then
        verify(memberRequestMapper).of(any(MemberLoginRequest.class));
        verify(memberFacade).login(any(MemberCommand.MemberLogin.class));
        verify(memberResponseMapper).of(any(MemberDTO.MemberLoginInfo.class));

        StepVerifier.create(flux.getResponseBody().log())
            .assertNext(response -> assertAll(() -> {
                assertEquals(HttpStatus.OK.value(), response.getRt());
                assertNotNull(response.getMemberToken());
                assertNotNull(response.getMemberName());
                assertNotNull(response.getAccessToken());
                assertNotNull(response.getRefreshToken());
            }))
            .verifyComplete();
    }
}