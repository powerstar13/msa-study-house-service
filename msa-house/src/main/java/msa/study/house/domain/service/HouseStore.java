package msa.study.house.domain.service;

import reactor.core.publisher.Mono;
import msa.study.house.application.dto.HouseCommand;
import msa.study.house.domain.House;
import msa.study.house.domain.service.dto.HouseDTO;

public interface HouseStore {

    Mono<House> houseRegister(HouseCommand.ExchangedHouseRegister command);

    Mono<Void> houseModify(HouseDTO.HouseAggregate houseAggregate, HouseCommand.HouseModify command);

    Mono<Void> houseDelete(HouseDTO.HouseAggregate houseAggregate);
}
