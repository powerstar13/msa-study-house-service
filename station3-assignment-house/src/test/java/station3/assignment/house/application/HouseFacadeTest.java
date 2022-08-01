package station3.assignment.house.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import station3.assignment.house.application.dto.HouseCommand;
import station3.assignment.house.application.dto.HouseCommandMapper;
import station3.assignment.house.domain.service.HouseService;
import station3.assignment.house.domain.service.dto.HouseDTO;
import station3.assignment.house.infrastructure.webClient.MemberWebClientService;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static station3.assignment.house.infrastructure.factory.HouseTestFactory.*;

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
        HouseCommand.HouseRegister command = houseRegisterCommand();

        given(houseWebClientService.exchangeMemberToken(any(String.class))).willReturn(exchangeMemberTokenResponseMono());
        given(houseCommandMapper.of(any(int.class), any(HouseCommand.HouseRegister.class))).willReturn(exchangedHouseRegisterCommand());
        given(houseService.houseRegister(any(HouseCommand.ExchangedHouseRegister.class))).willReturn(houseTokenInfoMono());

        Mono<HouseDTO.HouseTokenInfo> houseTokenInfoMono = houseFacade.houseRegister(command);

        verify(houseWebClientService).exchangeMemberToken(any(String.class));

        StepVerifier.create(houseTokenInfoMono.log())
            .assertNext(Assertions::assertNotNull)
            .verifyComplete();
    }

    @DisplayName("내방 수정")
    @Test
    void houseModify() {
        HouseCommand.HouseModify command = houseModifyCommand();

        given(houseService.houseModify(any(HouseCommand.HouseModify.class))).willReturn(Mono.empty());

        Mono<Void> voidMono = houseFacade.houseModify(command);

        verify(houseService).houseModify(any(HouseCommand.HouseModify.class));

        StepVerifier.create(voidMono.log())
            .expectNextCount(0)
            .verifyComplete();
    }

    @DisplayName("내방 삭제")
    @Test
    void houseDelete() {

        given(houseService.houseDelete(any(String.class))).willReturn(Mono.empty());

        Mono<Void> voidMono = houseFacade.houseDelete("houseToken");

        verify(houseService).houseDelete(any(String.class));

        StepVerifier.create(voidMono.log())
            .expectNextCount(0)
            .verifyComplete();
    }

    @DisplayName("내방 정보 조회")
    @Test
    void houseInfo() {

        given(houseService.houseInfo(any(String.class))).willReturn(houseInfoMono());

        Mono<HouseDTO.HouseInfo> houseInfoMono = houseFacade.houseInfo("houseToken");

        verify(houseService).houseInfo(any(String.class));

        StepVerifier.create(houseInfoMono.log())
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

        given(houseWebClientService.exchangeMemberToken(any(String.class))).willReturn(exchangeMemberTokenResponseMono());
        given(houseService.houseList(any(int.class))).willReturn(houseListMono());

        Mono<HouseDTO.HouseList> houseListMono = houseFacade.houseList("memberToken");

        verify(houseWebClientService).exchangeMemberToken(any(String.class));

        StepVerifier.create(houseListMono.log())
            .assertNext(houseList -> assertNotNull(houseList.getHouseList()))
            .verifyComplete();
    }

    @DisplayName("전체방 페이지 조회")
    @Test
    void housePage() {

        given(houseService.housePage(any(HouseCommand.HousePage.class))).willReturn(housePageMono());

        Mono<HouseDTO.HousePage> housePageMono = houseFacade.housePage(housePageCommand());

        verify(houseService).housePage(any(HouseCommand.HousePage.class));

        StepVerifier.create(housePageMono.log())
            .assertNext(houseList -> assertNotNull(houseList.getHouseList()))
            .verifyComplete();
    }
}