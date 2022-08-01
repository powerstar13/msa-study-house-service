package station3.assignment.house.infrastructure.dao;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import station3.assignment.house.application.dto.HouseCommand;
import station3.assignment.house.domain.Rental;

import java.util.List;

public interface HouseCustomRepository {

    Mono<List<Integer>> getHouseIdListOfHousePage(HouseCommand.HousePage command);
    
    Flux<Rental> getRentalListOfHousePage(HouseCommand.HousePage command, List<Integer> houseIdList);
}
