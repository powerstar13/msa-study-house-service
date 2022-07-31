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
    private final HouseReader houseReader;

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

    /**
     * 내방 수정 처리
     * @param command: 수정할 내방 정보
     */
    @Override
    public Mono<Void> houseModify(HouseCommand.HouseModify command) {

        return houseReader.findHouseAggregateInfo(command.getHouseToken()) // 1. 내방 정보 조회
            .flatMap(houseAggregate -> {
                return houseStore.houseModify(houseAggregate, command); // 2. 내방 수정
            });
    }

    /**
     * 내방 삭제 처리
     * @param houseToken: 방 대체 식별키
     */
    @Override
    public Mono<Void> houseDelete(String houseToken) {

        return houseReader.findHouseAggregateInfo(houseToken) // 1. 내방 정보 조회
            .flatMap(houseStore::houseDelete); // 2. 내방 삭제
    }
}
