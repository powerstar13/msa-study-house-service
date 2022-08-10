package msa.study.house.infrastructure.webClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import msa.study.house.infrastructure.webClient.response.ExchangeMemberTokenResponse;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberWebClientServiceTest {

    private static MemberWebClientService memberWebClientService;

    private static ObjectMapper objectMapper;

    public static MockWebServer mockWebServer;

    @BeforeAll
    static void setUp() throws IOException {
        objectMapper = new ObjectMapper();
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        memberWebClientService = new MemberWebClientService(mockWebServer.url("test").toString());
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @DisplayName("회원 고유번호 가져오기")
    @Test
    void exchangeMemberToken() throws JsonProcessingException {

        ExchangeMemberTokenResponse exchangeMemberTokenResponse = ExchangeMemberTokenResponse.builder()
            .rt(HttpStatus.OK.value())
            .rtMsg(HttpStatus.OK.getReasonPhrase())
            .memberId(RandomUtils.nextInt())
            .build();

        mockWebServer.enqueue(new MockResponse()
            .setBody(objectMapper.writeValueAsString(exchangeMemberTokenResponse))
            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        );

        Mono<ExchangeMemberTokenResponse> result = memberWebClientService.exchangeMemberToken(UUID.randomUUID().toString());

        StepVerifier.create(result.log())
            .assertNext(response -> assertAll(() -> {
                assertEquals(HttpStatus.OK.value(), response.getRt());
                assertTrue(response.getMemberId() > 0);
            }))
            .verifyComplete();
    }
}