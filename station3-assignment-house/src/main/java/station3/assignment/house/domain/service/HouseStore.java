package station3.assignment.house.domain.service;

import reactor.core.publisher.Mono;
import station3.assignment.house.application.dto.HouseCommand;
import station3.assignment.house.domain.House;

public interface HouseStore {

    Mono<House> houseRegister(HouseCommand.ExchangedHouseRegister command);
}
