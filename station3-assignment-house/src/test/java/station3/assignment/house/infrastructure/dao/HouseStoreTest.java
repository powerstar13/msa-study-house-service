package station3.assignment.house.infrastructure.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import station3.assignment.house.application.dto.HouseCommand;
import station3.assignment.house.domain.House;
import station3.assignment.house.domain.Rental;
import station3.assignment.house.domain.service.HouseStore;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static station3.assignment.house.infrastructure.factory.HouseTestFactory.*;

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

        Mono<House> houseMono = houseStore.houseRegister(command);

        verify(houseRepository).save(any(House.class));

        StepVerifier.create(houseMono.log())
            .assertNext(house -> assertNotNull(house.getHouseToken()))
            .verifyComplete();
    }
}