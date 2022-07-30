package station3.assignment.house.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import station3.assignment.house.application.dto.HouseCommand;
import station3.assignment.house.domain.service.dto.HouseDTO;

@Component
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseStore houseStore;

    /**
     * 내방 등록 처리
     * @param command: 등록할 내방 정보
     * @return HouseTokenInfo: 방 대체 식별키
     */
    @Override
    public Mono<HouseDTO.HouseTokenInfo> houseRegister(HouseCommand.ExchangedHouseRegister command) {

        return houseStore.houseRegister(command) // 내방 등록
            .flatMap(house -> Mono.just(new HouseDTO.HouseTokenInfo(house.getHouseToken())));
    }
}
