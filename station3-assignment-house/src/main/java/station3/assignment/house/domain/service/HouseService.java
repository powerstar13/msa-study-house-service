package station3.assignment.house.domain.service;

import reactor.core.publisher.Mono;
import station3.assignment.house.application.dto.HouseCommand;
import station3.assignment.house.domain.service.dto.HouseDTO;

public interface HouseService {

    Mono<HouseDTO.HouseTokenInfo> houseRegister(HouseCommand.ExchangedHouseRegister command);

    Mono<Void> houseModify(HouseCommand.HouseModify command);

    Mono<Void> houseDelete(String houseToken);

    Mono<HouseDTO.HouseInfo> houseInfo(String houseToken);
}
