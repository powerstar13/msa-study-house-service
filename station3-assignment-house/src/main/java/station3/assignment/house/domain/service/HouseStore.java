package station3.assignment.house.domain.service;

import reactor.core.publisher.Mono;
import station3.assignment.house.application.dto.HouseCommand;
import station3.assignment.house.domain.House;
import station3.assignment.house.domain.service.dto.HouseDTO;

public interface HouseStore {

    Mono<House> houseRegister(HouseCommand.ExchangedHouseRegister command);

    Mono<Void> houseModify(HouseDTO.HouseAggregate houseAggregate, HouseCommand.HouseModify command);
}
