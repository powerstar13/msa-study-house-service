package station3.assignment.house.presentation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import station3.assignment.house.application.dto.HouseCommand;
import station3.assignment.house.application.HouseFacade;
import station3.assignment.house.domain.service.dto.HouseDTO;
import station3.assignment.house.infrastructure.exception.GlobalExceptionHandler;
import station3.assignment.house.infrastructure.router.RouterPathPattern;
import station3.assignment.house.presentation.request.HouseModifyRequest;
import station3.assignment.house.presentation.request.HouseRegisterRequest;
import station3.assignment.house.presentation.request.HouseRequestMapper;
import station3.assignment.house.presentation.response.HouseInfoResponse;
import station3.assignment.house.presentation.response.HouseRegisterResponse;
import station3.assignment.house.presentation.response.HouseResponseMapper;
import station3.assignment.house.presentation.shared.WebFluxSharedHandlerTest;
import station3.assignment.house.presentation.shared.response.SuccessResponse;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static station3.assignment.house.infrastructure.factory.HouseTestFactory.*;
import static station3.assignment.house.infrastructure.restdocs.RestdocsDocumentFormat.houseTypeFormat;
import static station3.assignment.house.infrastructure.restdocs.RestdocsDocumentFormat.rentalTypeFormat;
import static station3.assignment.house.infrastructure.restdocs.RestdocsDocumentUtil.requestPrettyPrint;
import static station3.assignment.house.infrastructure.restdocs.RestdocsDocumentUtil.responsePrettyPrint;

@WebFluxTest(HouseHandler.class)
class HouseHandlerTest extends WebFluxSharedHandlerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private GlobalExceptionHandler globalExceptionHandler;

    @MockBean
    private HouseFacade houseFacade;
    @MockBean
    private HouseRequestMapper houseRequestMapper;
    @MockBean
    private HouseResponseMapper houseResponseMapper;

    @DisplayName("내방 등록")
    @Test
    void houseRegister() {
        // given
        given(houseRequestMapper.of(any(HouseRegisterRequest.class))).willReturn(houseRegisterCommand());
        given(houseFacade.houseRegister(any(HouseCommand.HouseRegister.class))).willReturn(houseTokenInfoMono());
        given(houseResponseMapper.of(any(HouseDTO.HouseTokenInfo.class))).willReturn(houseRegisterResponse());

        // when
        final String URI = RouterPathPattern.HOUSE_REGISTER.getFullPath();
        WebTestClient.ResponseSpec result = webClient
            .post()
            .uri(URI)
            .header(AUTHORIZATION, "accessToken")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(houseRegisterRequest())
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

        result.expectStatus().isOk()
            .expectBody()
            .consumeWith(document(URI,
                requestPrettyPrint(),
                responsePrettyPrint(),
                requestFields(
                    fieldWithPath("memberToken").type(JsonFieldType.STRING).description("회원 대체 식별키"),
                    fieldWithPath("houseAddress").type(JsonFieldType.STRING).description("방 주소"),
                    fieldWithPath("houseType").type(JsonFieldType.STRING).description("방 유형").attributes(houseTypeFormat()),
                    fieldWithPath("rentalList[]").type(JsonFieldType.ARRAY).description("임대료 정보 목록"),
                    fieldWithPath("rentalList[].rentalType").type(JsonFieldType.STRING).description("임대 유형").attributes(rentalTypeFormat()),
                    fieldWithPath("rentalList[].deposit").type(JsonFieldType.NUMBER).description("보증금"),
                    fieldWithPath("rentalList[].rent").type(JsonFieldType.NUMBER).description("월세 (임대 유형이 월세의 경우 필수)").optional()
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("결과 코드"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("결과 메시지"),
                    fieldWithPath("houseToken").type(JsonFieldType.STRING).description("방 대체 식별키")
                )
            ));

        FluxExchangeResult<HouseRegisterResponse> flux = result.returnResult(HouseRegisterResponse.class);

        // then
        verify(houseRequestMapper).of(any(HouseRegisterRequest.class));
        verify(houseFacade).houseRegister(any(HouseCommand.HouseRegister.class));
        verify(houseResponseMapper).of(any(HouseDTO.HouseTokenInfo.class));

        StepVerifier.create(flux.getResponseBody().log())
            .assertNext(response -> assertAll(() -> {
                assertEquals(HttpStatus.CREATED.value(), response.getRt());
                assertInstanceOf(String.class, response.getHouseToken());
            }))
            .verifyComplete();
    }

    @DisplayName("내방 수정")
    @Test
    void houseModify() {
        // given
        given(houseRequestMapper.of(any(HouseModifyRequest.class))).willReturn(houseModifyCommand());
        given(houseFacade.houseModify(any(HouseCommand.HouseModify.class))).willReturn(Mono.empty());

        // when
        final String URI = RouterPathPattern.HOUSE_MODIFY.getFullPath();
        WebTestClient.ResponseSpec result = webClient
            .put()
            .uri(URI)
            .header(AUTHORIZATION, "accessToken")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(houseModifyRequest())
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

        result.expectStatus().isOk()
            .expectBody()
            .consumeWith(document(URI,
                requestPrettyPrint(),
                responsePrettyPrint(),
                requestFields(
                    fieldWithPath("houseToken").type(JsonFieldType.STRING).description("방 대체 식별키"),
                    fieldWithPath("houseAddress").type(JsonFieldType.STRING).description("방 주소"),
                    fieldWithPath("houseType").type(JsonFieldType.STRING).description("방 유형").attributes(houseTypeFormat()),
                    fieldWithPath("rentalList[]").type(JsonFieldType.ARRAY).description("임대료 정보 목록"),
                    fieldWithPath("rentalList[].rentalToken").type(JsonFieldType.STRING).description("임대료 대체 식별키 (수정할 경우 필수)").optional(),
                    fieldWithPath("rentalList[].rentalType").type(JsonFieldType.STRING).description("임대 유형").attributes(rentalTypeFormat()),
                    fieldWithPath("rentalList[].deposit").type(JsonFieldType.NUMBER).description("보증금"),
                    fieldWithPath("rentalList[].rent").type(JsonFieldType.NUMBER).description("월세 (임대 유형이 월세의 경우 필수)").optional()
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("결과 코드"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("결과 메시지")
                )
            ));

        FluxExchangeResult<SuccessResponse> flux = result.returnResult(SuccessResponse.class);

        // then
        verify(houseRequestMapper).of(any(HouseModifyRequest.class));
        verify(houseFacade).houseModify(any(HouseCommand.HouseModify.class));

        StepVerifier.create(flux.getResponseBody().log())
            .assertNext(response -> assertEquals(HttpStatus.OK.value(), response.getRt()))
            .verifyComplete();
    }

    @DisplayName("내방 삭제")
    @Test
    void houseDelete() {
        // given
        given(houseFacade.houseDelete(any(String.class))).willReturn(Mono.empty());

        // when
        final String URI = RouterPathPattern.HOUSE_DELETE.getFullPath();
        WebTestClient.ResponseSpec result = webClient
            .delete()
            .uri(URI, UUID.randomUUID().toString())
            .header(AUTHORIZATION, "accessToken")
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

        result.expectStatus().isOk()
            .expectBody()
            .consumeWith(document(URI,
                requestPrettyPrint(),
                responsePrettyPrint(),
                pathParameters(
                    parameterWithName("houseToken").description("방 대체 식별키")
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("결과 코드"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("결과 메시지")
                )
            ));

        FluxExchangeResult<SuccessResponse> flux = result.returnResult(SuccessResponse.class);

        // then
        verify(houseFacade).houseDelete(any(String.class));

        StepVerifier.create(flux.getResponseBody().log())
            .assertNext(response -> assertEquals(HttpStatus.OK.value(), response.getRt()))
            .verifyComplete();
    }

    @DisplayName("내방 정보 조회")
    @Test
    void houseInfo() {
        // given
        given(houseFacade.houseInfo(any(String.class))).willReturn(houseInfoMono());
        given(houseResponseMapper.of(any(HouseDTO.HouseInfo.class))).willReturn(houseInfoResponse());

        // when
        final String URI = RouterPathPattern.HOUSE_INFO.getFullPath();
        WebTestClient.ResponseSpec result = webClient
            .get()
            .uri(URI, UUID.randomUUID().toString())
            .header(AUTHORIZATION, "accessToken")
            .accept(MediaType.APPLICATION_JSON)
            .exchange();

        result.expectStatus().isOk()
            .expectBody()
            .consumeWith(document(URI,
                requestPrettyPrint(),
                responsePrettyPrint(),
                pathParameters(
                    parameterWithName("houseToken").description("방 대체 식별키")
                ),
                responseFields(
                    fieldWithPath("rt").type(JsonFieldType.NUMBER).description("결과 코드"),
                    fieldWithPath("rtMsg").type(JsonFieldType.STRING).description("결과 메시지"),
                    fieldWithPath("houseToken").type(JsonFieldType.STRING).description("방 대체 식별키"),
                    fieldWithPath("houseAddress").type(JsonFieldType.STRING).description("방 주소"),
                    fieldWithPath("houseType").type(JsonFieldType.STRING).description("방 유형").attributes(houseTypeFormat()),
                    fieldWithPath("rentalList[]").type(JsonFieldType.ARRAY).description("임대료 정보 목록"),
                    fieldWithPath("rentalList[].rentalToken").type(JsonFieldType.STRING).description("임대료 대체 식별키"),
                    fieldWithPath("rentalList[].rentalType").type(JsonFieldType.STRING).description("임대 유형").attributes(rentalTypeFormat()),
                    fieldWithPath("rentalList[].deposit").type(JsonFieldType.NUMBER).description("보증금"),
                    fieldWithPath("rentalList[].rent").type(JsonFieldType.NUMBER).description("월세").optional()
                )
            ));

        FluxExchangeResult<HouseInfoResponse> flux = result.returnResult(HouseInfoResponse.class);

        // then
        verify(houseFacade).houseInfo(any(String.class));
        verify(houseResponseMapper).of(any(HouseDTO.HouseInfo.class));

        StepVerifier.create(flux.getResponseBody().log())
            .assertNext(response -> assertAll(() -> {
                assertEquals(HttpStatus.OK.value(), response.getRt());
                assertNotNull(response.getHouseToken());
                assertNotNull(response.getHouseAddress());
                assertNotNull(response.getHouseType());
            }))
            .verifyComplete();
    }
}