package station3.assignment.house.domain.service;

import reactor.core.publisher.Mono;
import station3.assignment.house.application.dto.HouseCommand;
import station3.assignment.house.domain.service.dto.HouseDTO;

public interface HouseReader {

    Mono<HouseDTO.HouseAggregate> findHouseAggregateInfo(String houseToken);

    Mono<HouseDTO.HouseList> findAllHouseAggregateByMemberId(int memberId);

    Mono<HouseDTO.HousePage> findAllByPageable(HouseCommand.HousePage command);
}
