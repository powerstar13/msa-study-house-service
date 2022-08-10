package msa.study.house.domain.service;

import msa.study.house.application.dto.HouseCommand;
import msa.study.house.domain.service.dto.HouseDTO;
import reactor.core.publisher.Mono;

public interface HouseReader {

    Mono<HouseDTO.HouseAggregate> findHouseAggregateInfo(String houseToken);

    Mono<HouseDTO.HouseList> findAllHouseAggregateByMemberId(int memberId);

    Mono<HouseDTO.HousePage> findAllHousePage(HouseCommand.HousePage command);
}
