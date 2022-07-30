package station3.assignment.house.domain.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import station3.assignment.house.application.dto.HouseCommand;
import station3.assignment.house.domain.service.dto.HouseDTO;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static station3.assignment.house.infrastructure.factory.HouseTestFactory.exchangedHouseRegisterCommand;
import static station3.assignment.house.infrastructure.factory.HouseTestFactory.houseMono;

@SpringBootTest
class HouseServiceTest {

    @Autowired
    private HouseService houseService;

    @MockBean
    private HouseStore houseStore;

    @DisplayName("내방 등록")
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
}