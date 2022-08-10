package msa.study.house.domain.service;

import reactor.core.publisher.Mono;
import msa.study.house.application.dto.HouseCommand;
import msa.study.house.domain.service.dto.HouseDTO;

public interface HouseService {

    Mono<HouseDTO.HouseTokenInfo> houseRegister(HouseCommand.ExchangedHouseRegister command);

    Mono<Void> houseModify(HouseCommand.HouseModify command);

    Mono<Void> houseDelete(String houseToken);

    Mono<HouseDTO.HouseInfo> houseInfo(String houseToken);

    Mono<HouseDTO.HouseList> houseList(int memberId);

    Mono<HouseDTO.HousePage> housePage(HouseCommand.HousePage command);
}
