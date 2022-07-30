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
}