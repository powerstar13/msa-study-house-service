package msa.study.house.domain.service;

import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import msa.study.house.application.dto.HouseCommand;
import msa.study.house.domain.House;
import msa.study.house.domain.service.dto.HouseDTO;
import msa.study.house.domain.service.dto.HouseDTOMapper;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static msa.study.house.infrastructure.factory.HouseTestFactory.*;

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

        Mono<HouseDTO.HouseTokenInfo> result = houseService.houseRegister(command);

        StepVerifier.create(result.log())
            .assertNext(Assertions::assertNotNull)
            .verifyComplete();
    }

    @DisplayName("내방 수정")
    @Test
    void houseModify() {
        HouseCommand.HouseModify command = houseModifyCommand();

        given(houseReader.findHouseAggregateInfo(anyString())).willReturn(houseAggregateMono());
        given(houseStore.houseModify(any(HouseDTO.HouseAggregate.class), any(HouseCommand.HouseModify.class))).willReturn(Mono.empty());

        Mono<Void> result = houseService.houseModify(command);

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }

    @DisplayName("내방 삭제")
    @Test
    void houseDelete() {

        given(houseReader.findHouseAggregateInfo(anyString())).willReturn(houseAggregateMono());
        given(houseStore.houseDelete(any(HouseDTO.HouseAggregate.class))).willReturn(Mono.empty());

        Mono<Void> result = houseService.houseDelete(UUID.randomUUID().toString());

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }

    @DisplayName("내방 정보 조회")
    @Test
    void houseInfo() {

        given(houseReader.findHouseAggregateInfo(anyString())).willReturn(houseAggregateMono());
        given(houseDTOMapper.of(any(House.class), anyList())).willReturn(houseInfoDTO());

        Mono<HouseDTO.HouseInfo> result = houseService.houseInfo("houseToken");

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

        given(houseReader.findAllHouseAggregateByMemberId(any(int.class))).willReturn(houseListMono());

        Mono<HouseDTO.HouseList> result = houseService.houseList(RandomUtils.nextInt());

        StepVerifier.create(result.log())
            .assertNext(houseList -> assertNotNull(houseList.getHouseList()))
            .verifyComplete();
    }

    @DisplayName("전체방 페이지 조회")
    @Test
    void housePage() {

        given(houseReader.findAllHousePage(any(HouseCommand.HousePage.class))).willReturn(housePageMono());

        Mono<HouseDTO.HousePage> result = houseService.housePage(housePageCommand());

        StepVerifier.create(result.log())
            .assertNext(houseList -> assertNotNull(houseList.getHouseList()))
            .verifyComplete();
    }
}