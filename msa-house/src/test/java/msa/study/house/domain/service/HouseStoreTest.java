package msa.study.house.domain.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import msa.study.house.application.dto.HouseCommand;
import msa.study.house.domain.House;
import msa.study.house.domain.Rental;
import msa.study.house.domain.service.dto.HouseDTO;
import msa.study.house.infrastructure.dao.HouseRepository;
import msa.study.house.infrastructure.dao.RentalRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static msa.study.house.infrastructure.factory.HouseTestFactory.*;

@SpringBootTest
class HouseStoreTest {

    @Autowired
    private HouseStore houseStore;

    @MockBean
    private HouseRepository houseRepository;
    @MockBean
    private RentalRepository rentalRepository;

    @DisplayName("내방 등록 및 임대료 등록")
    @Test
    void houseRegister() {
        HouseCommand.ExchangedHouseRegister command = exchangedHouseRegisterCommand();

        given(houseRepository.save(any(House.class))).willReturn(houseMono());
        given(rentalRepository.saveAll(anyList())).willReturn(rentalFlux());

        Mono<House> result = houseStore.houseRegister(command);

        verify(houseRepository).save(any(House.class));

        StepVerifier.create(result.log())
            .assertNext(house -> assertNotNull(house.getHouseToken()))
            .verifyComplete();
    }

    @DisplayName("내방 정보 수정")
    @Test
    void houseModify() {

        HouseDTO.HouseAggregate houseAggregate = houseAggregate();
        HouseCommand.HouseModify command = houseModifyCommand();

        given(houseRepository.save(any(House.class))).willReturn(houseMono());
        given(rentalRepository.delete(any(Rental.class))).willReturn(Mono.empty());
        given(rentalRepository.saveAll(anyList())).willReturn(rentalFlux());

        Mono<Void> result = houseStore.houseModify(houseAggregate, command);

        verify(houseRepository).save(any(House.class));

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }

    @DisplayName("내방 정보 삭제")
    @Test
    void houseDelete() {
        HouseDTO.HouseAggregate houseAggregate = houseAggregate();

        given(rentalRepository.delete(any(Rental.class))).willReturn(Mono.empty());
        given(houseRepository.delete(any(House.class))).willReturn(Mono.empty());

        Mono<Void> result = houseStore.houseDelete(houseAggregate);

        verify(houseRepository).delete(any(House.class));

        StepVerifier.create(result.log())
            .expectNextCount(0)
            .verifyComplete();
    }
}