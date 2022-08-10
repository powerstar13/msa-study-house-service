package msa.study.house.domain.service;

import msa.study.house.infrastructure.dao.HouseRepository;
import msa.study.house.infrastructure.dao.RentalRepository;
import msa.study.house.infrastructure.factory.HouseTestFactory;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import msa.study.house.application.dto.HouseCommand;
import msa.study.house.domain.House;
import msa.study.house.domain.service.dto.HouseDTO;
import msa.study.house.domain.service.dto.HouseDTOMapper;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class HouseReaderTest {

    @Autowired
    private HouseReader houseReader;

    @MockBean
    private HouseRepository houseRepository;
    @MockBean
    private RentalRepository rentalRepository;
    @MockBean
    private HouseDTOMapper houseDTOMapper;

    @DisplayName("방 정보 조회")
    @Test
    void findHouseAggregateInfo() {

        given(houseRepository.findByHouseToken(anyString())).willReturn(HouseTestFactory.houseMono());
        given(rentalRepository.findAllByHouseId(any(int.class))).willReturn(HouseTestFactory.rentalFlux());
        given(houseDTOMapper.of(any(House.class), any(Flux.class))).willReturn(HouseTestFactory.houseAggregate());

        Mono<HouseDTO.HouseAggregate> result = houseReader.findHouseAggregateInfo(UUID.randomUUID().toString());

        verify(houseRepository).findByHouseToken(anyString());

        StepVerifier.create(result.log())
            .assertNext(houseAggregate -> assertNotNull(houseAggregate.getHouse()))
            .verifyComplete();
    }

    @DisplayName("방 목록 조회")
    @Test
    void findAllHouseAggregateByMemberId() {

        given(houseRepository.findAllByMemberId(any(int.class))).willReturn(HouseTestFactory.houseFlux());
        given(rentalRepository.findAllByHouseId(any(int.class))).willReturn(HouseTestFactory.rentalFlux());
        given(houseDTOMapper.of(any(House.class), anyList())).willReturn(HouseTestFactory.houseInfoDTO());

        Mono<HouseDTO.HouseList> result = houseReader.findAllHouseAggregateByMemberId(RandomUtils.nextInt());

        verify(houseRepository).findAllByMemberId(any(int.class));

        StepVerifier.create(result.log())
            .assertNext(houseList -> assertNotNull(houseList.getHouseList()))
            .verifyComplete();
    }

    @DisplayName("전체방 페이지 조회")
    @Test
    void findAllHousePage() {

        given(houseRepository.getHouseIdListOfHousePage(any(HouseCommand.HousePage.class))).willReturn(Mono.just(Arrays.asList(1, 2)));
        given(houseRepository.findAllByHouseIdIn(anyList(), any(PageRequest.class))).willReturn(HouseTestFactory.houseFlux());
        given(rentalRepository.getRentalListOfHousePage(any(HouseCommand.HousePage.class), anyList())).willReturn(HouseTestFactory.rentalFlux());
        given(houseDTOMapper.of(any(House.class), anyList())).willReturn(HouseTestFactory.houseInfoDTO());
        given(houseDTOMapper.of(any(HouseDTO.pageInfo.class), anyList())).willReturn(HouseTestFactory.housePageDTO());

        Mono<HouseDTO.HousePage> result = houseReader.findAllHousePage(HouseTestFactory.housePageCommand());

        verify(houseRepository).getHouseIdListOfHousePage(any(HouseCommand.HousePage.class));

        StepVerifier.create(result.log())
            .assertNext(housePage -> assertNotNull(housePage.getHouseList()))
            .verifyComplete();
    }
}