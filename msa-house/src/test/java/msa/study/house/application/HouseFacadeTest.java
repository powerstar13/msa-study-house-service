package msa.study.house.application;

import msa.study.house.domain.service.HouseService;
import msa.study.house.infrastructure.factory.HouseTestFactory;
import msa.study.house.infrastructure.webClient.MemberWebClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import msa.study.house.application.dto.HouseCommand;
import msa.study.house.application.dto.HouseCommandMapper;
import msa.study.house.domain.service.dto.HouseDTO;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class HouseFacadeTest {

    @Autowired
    private HouseFacade houseFacade;

    @MockBean
    private MemberWebClientService houseWebClientService;
    @MockBean
    private HouseCommandMapper houseCommandMapper;
    @MockBean
    private HouseService houseService;

    @DisplayName("내방 등록")
    @Test
    void houseRegister() {
        HouseCommand.HouseRegister command = HouseTestFactory.houseRegisterCommand();

        given(houseWebClientService.exchangeMemberToken(anyString())).willReturn(HouseTestFactory.exchangeMemberTokenResponseMono());
        given(houseCommandMapper.of(any(int.class), any(HouseCommand.HouseRegister.class))).willReturn(HouseTestFactory.exchangedHouseRegisterCommand());
        given(houseService.houseRegister(any(HouseCommand.ExchangedHouseRegister.class))).willReturn(HouseTestFactory.houseTokenInfoMono());

        Mono<HouseDTO.HouseTokenInfo> result = houseFacade.houseRegister(command);

        verify(houseWebClientService).exchangeMemberToken(anyString());

        StepVerifier.create(result.log())
            .assertNext(Assertions::assertNotNull)
            .verifyComplete();
    }

    @DisplayName("내방 수정")
    @Test
    void houseModify() {
        HouseCommand.HouseModify command = HouseTestFactory.houseModifyCommand();

        given(houseService.houseModify(any(HouseCommand.HouseModify.class))).willReturn(Mono.empty());

        Mono<Void> result = houseFacade.houseModify(command);

        verify(houseService).houseModify(any(HouseCommand.HouseModify.class));

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }

    @DisplayName("내방 삭제")
    @Test
    void houseDelete() {

        given(houseService.houseDelete(anyString())).willReturn(Mono.empty());

        Mono<Void> result = houseFacade.houseDelete("houseToken");

        verify(houseService).houseDelete(anyString());

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }

    @DisplayName("내방 정보 조회")
    @Test
    void houseInfo() {

        given(houseService.houseInfo(anyString())).willReturn(HouseTestFactory.houseInfoMono());

        Mono<HouseDTO.HouseInfo> result = houseFacade.houseInfo("houseToken");

        verify(houseService).houseInfo(anyString());

        StepVerifier.create(result.log())
            .assertNext(houseInfo -> assertAll(() -> {
                assertNotNull(houseInfo.getHouseToken());
                assertNotNull(houseInfo.getHouseAddress());
                assertNotNull(houseInfo.getHouseType());
            }))
            .verifyComplete();
    }

    @DisplayName("내방 목록 조회")
    @Test
    void houseList() {

        given(houseWebClientService.exchangeMemberToken(anyString())).willReturn(HouseTestFactory.exchangeMemberTokenResponseMono());
        given(houseService.houseList(any(int.class))).willReturn(HouseTestFactory.houseListMono());

        Mono<HouseDTO.HouseList> result = houseFacade.houseList("memberToken");

        verify(houseWebClientService).exchangeMemberToken(anyString());

        StepVerifier.create(result.log())
            .assertNext(houseList -> assertNotNull(houseList.getHouseList()))
            .verifyComplete();
    }

    @DisplayName("전체방 페이지 조회")
    @Test
    void housePage() {

        given(houseService.housePage(any(HouseCommand.HousePage.class))).willReturn(HouseTestFactory.housePageMono());

        Mono<HouseDTO.HousePage> result = houseFacade.housePage(HouseTestFactory.housePageCommand());

        verify(houseService).housePage(any(HouseCommand.HousePage.class));

        StepVerifier.create(result.log())
            .assertNext(houseList -> assertNotNull(houseList.getHouseList()))
            .verifyComplete();
    }
}