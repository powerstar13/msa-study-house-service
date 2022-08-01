package station3.assignment.house.domain.service;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import station3.assignment.house.application.dto.HouseCommand;
import station3.assignment.house.domain.House;
import station3.assignment.house.domain.service.dto.HouseDTO;
import station3.assignment.house.domain.service.dto.HouseDTOMapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static station3.assignment.house.infrastructure.factory.HouseTestFactory.*;
import static station3.assignment.house.infrastructure.factory.HouseTestFactory.housePageDTO;

@SpringBootTest
class HouseServiceTest {

    @Autowired
    private HouseService houseService;

    @MockBean
    private HouseStore houseStore;
    @MockBean
    private HouseReader houseReader;
    @MockBean
    private HouseDTOMapper houseDTOMapper;

    @DisplayName("내방 등록 처리")
    @Test
    void houseRegister() {
        HouseCommand.ExchangedHouseRegister command = exchangedHouseRegisterCommand();

        given(houseStore.houseRegister(any(HouseCommand.ExchangedHouseRegister.class))).willReturn(houseMono());

        Mono<HouseDTO.HouseTokenInfo> houseTokenInfoMono = houseService.houseRegister(command);

        verify(houseStore).houseRegister(any(HouseCommand.ExchangedHouseRegister.class));

        StepVerifier.create(houseTokenInfoMono.log())
            .assertNext(Assertions::assertNotNull)
            .verifyComplete();
    }

    @DisplayName("내방 수정")
    @Test
    void houseModify() {
        HouseCommand.HouseModify command = houseModifyCommand();

        given(houseReader.findHouseAggregateInfo(any(String.class))).willReturn(houseAggregateMono());
        given(houseStore.houseModify(any(HouseDTO.HouseAggregate.class), any(HouseCommand.HouseModify.class))).willReturn(Mono.empty());

        Mono<Void> voidMono = houseService.houseModify(command);

        verify(houseReader).findHouseAggregateInfo(any(String.class));

        StepVerifier.create(voidMono.log())
            .expectNextCount(0)
            .verifyComplete();
    }

    @DisplayName("내방 삭제")
    @Test
    void houseDelete() {

        given(houseReader.findHouseAggregateInfo(any(String.class))).willReturn(houseAggregateMono());
        given(houseStore.houseDelete(any(HouseDTO.HouseAggregate.class))).willReturn(Mono.empty());

        Mono<Void> voidMono = houseService.houseDelete("houseToken");

        verify(houseReader).findHouseAggregateInfo(any(String.class));

        StepVerifier.create(voidMono.log())
            .expectNextCount(0)
            .verifyComplete();
    }

    @DisplayName("내방 정보 조회")
    @Test
    void houseInfo() {

        given(houseReader.findHouseAggregateInfo(any(String.class))).willReturn(houseAggregateMono());
        given(houseDTOMapper.of(any(House.class), anyList())).willReturn(houseInfoDTO());

        Mono<HouseDTO.HouseInfo> houseInfoMono = houseService.houseInfo("houseToken");

        verify(houseReader).findHouseAggregateInfo(any(String.class));

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

        given(houseReader.findAllHouseAggregateByMemberId(any(int.class))).willReturn(houseListMono());

        Mono<HouseDTO.HouseList> houseListMono = houseService.houseList(RandomUtils.nextInt());

        verify(houseReader).findAllHouseAggregateByMemberId(any(int.class));

        StepVerifier.create(houseListMono.log())
            .assertNext(houseList -> assertNotNull(houseList.getHouseList()))
            .verifyComplete();
    }

    @DisplayName("전체방 페이지 조회")
    @Test
    void housePage() {

        given(houseReader.findAllHousePage(any(HouseCommand.HousePage.class))).willReturn(housePageMono());

        Mono<HouseDTO.HousePage> housePageMono = houseService.housePage(housePageCommand());

        verify(houseReader).findAllHousePage(any(HouseCommand.HousePage.class));

        StepVerifier.create(housePageMono.log())
            .assertNext(houseList -> assertNotNull(houseList.getHouseList()))
            .verifyComplete();
    }
}