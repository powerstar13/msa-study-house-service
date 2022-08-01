package station3.assignment.house.domain.service;

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
import station3.assignment.house.application.dto.HouseCommand;
import station3.assignment.house.domain.House;
import station3.assignment.house.domain.Rental;
import station3.assignment.house.domain.service.dto.HouseDTO;
import station3.assignment.house.domain.service.dto.HouseDTOMapper;
import station3.assignment.house.infrastructure.dao.HouseRepository;
import station3.assignment.house.infrastructure.dao.RentalRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static station3.assignment.house.infrastructure.factory.HouseTestFactory.*;

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

        given(houseRepository.findByHouseToken(any(String.class))).willReturn(houseMono());
        given(rentalRepository.findAllByHouseId(any(int.class))).willReturn(rentalFlux());
        given(houseDTOMapper.of(any(House.class), any(Flux.class))).willReturn(houseAggregate());

        Mono<HouseDTO.HouseAggregate> houseAggregateMono = houseReader.findHouseAggregateInfo("houseToken");

        verify(houseRepository).findByHouseToken(any(String.class));

        StepVerifier.create(houseAggregateMono.log())
            .assertNext(houseAggregate -> assertNotNull(houseAggregate.getHouse()))
            .verifyComplete();
    }

    @DisplayName("방 목록 조회")
    @Test
    void findAllHouseAggregateByMemberId() {

        given(houseRepository.findAllByMemberId(any(int.class))).willReturn(houseFlux());
        given(rentalRepository.findAllByHouseId(any(int.class))).willReturn(rentalFlux());
        given(houseDTOMapper.of(any(House.class), anyList())).willReturn(houseInfoDTO());

        Mono<HouseDTO.HouseList> houseListMono = houseReader.findAllHouseAggregateByMemberId(RandomUtils.nextInt());

        verify(houseRepository).findAllByMemberId(any(int.class));

        StepVerifier.create(houseListMono.log())
            .assertNext(houseList -> assertNotNull(houseList.getHouseList()))
            .verifyComplete();
    }

    @DisplayName("전체방 페이지 조회")
    @Test
    void findAllHousePage() {

        given(houseRepository.getHouseIdListOfHousePage(any(HouseCommand.HousePage.class))).willReturn(Mono.just(Arrays.asList(1, 2)));
        given(houseRepository.findAllByHouseIdIn(anyList(), any(PageRequest.class))).willReturn(houseFlux());
        given(rentalRepository.getRentalListOfHousePage(any(HouseCommand.HousePage.class), anyList())).willReturn(rentalFlux());
        given(houseDTOMapper.of(any(House.class), anyList())).willReturn(houseInfoDTO());
        given(houseDTOMapper.of(any(HouseDTO.pageInfo.class), anyList())).willReturn(housePageDTO());

        Mono<HouseDTO.HousePage> housePageMono = houseReader.findAllHousePage(housePageCommand());

        verify(houseRepository).getHouseIdListOfHousePage(any(HouseCommand.HousePage.class));

        StepVerifier.create(housePageMono.log())
            .assertNext(housePage -> assertNotNull(housePage.getHouseList()))
            .verifyComplete();
    }
}