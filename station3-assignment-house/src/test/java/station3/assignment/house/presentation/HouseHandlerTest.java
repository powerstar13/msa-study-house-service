package station3.assignment.house.presentation;

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
import station3.assignment.house.application.dto.HouseCommand;
import station3.assignment.house.application.HouseFacade;
import station3.assignment.house.domain.service.dto.HouseDTO;
import station3.assignment.house.infrastructure.exception.GlobalExceptionHandler;
import station3.assignment.house.infrastructure.router.RouterPathPattern;
import station3.assignment.house.presentation.request.HouseRegisterRequest;
import station3.assignment.house.presentation.request.HouseRequestMapper;
import station3.assignment.house.presentation.response.HouseRegisterResponse;
import station3.assignment.house.presentation.response.HouseResponseMapper;
import station3.assignment.house.presentation.shared.WebFluxSharedHandlerTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
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
                    fieldWithPath("rentalRegisterRequestList[]").type(JsonFieldType.ARRAY).description("임대료 정보 목록"),
                    fieldWithPath("rentalRegisterRequestList[].rentalType").type(JsonFieldType.STRING).description("임대 유형").attributes(rentalTypeFormat()),
                    fieldWithPath("rentalRegisterRequestList[].deposit").type(JsonFieldType.NUMBER).description("보증금"),
                    fieldWithPath("rentalRegisterRequestList[].rent").type(JsonFieldType.NUMBER).description("월세").optional()
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
}