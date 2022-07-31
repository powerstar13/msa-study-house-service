package station3.assignment.house.domain.service;

import reactor.core.publisher.Mono;
import station3.assignment.house.domain.service.dto.HouseDTO;

public interface HouseReader {

    Mono<HouseDTO.HouseAggregate> findHouseAggregateInfo(String houseToken);
}
