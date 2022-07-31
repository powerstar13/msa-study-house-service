package station3.assignment.house.domain.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import station3.assignment.house.domain.service.dto.HouseDTO;
import station3.assignment.house.infrastructure.dao.HouseRepository;
import station3.assignment.house.infrastructure.dao.RentalRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static station3.assignment.house.infrastructure.factory.HouseTestFactory.houseMono;
import static station3.assignment.house.infrastructure.factory.HouseTestFactory.rentalFlux;

@SpringBootTest
class HouseReaderTest {

    @Autowired
    private HouseReader houseReader;

    @MockBean
    private HouseRepository houseRepository;
    @MockBean
    private RentalRepository rentalRepository;

    @DisplayName("방 정보 조회")
    @Test
    void findHouseAndRentalInfo() {

        given(houseRepository.findByHouseToken(any(String.class))).willReturn(houseMono());
        given(rentalRepository.findByHouseId(any(int.class))).willReturn(rentalFlux());

        Mono<HouseDTO.HouseAggregate> houseAggregateMono = houseReader.findHouseAggregateInfo("houseToken");

        verify(houseRepository).findByHouseToken(any(String.class));

        StepVerifier.create(houseAggregateMono.log())
            .assertNext(houseAggregate -> assertNotNull(houseAggregate.getHouse()))
            .verifyComplete();
    }
}