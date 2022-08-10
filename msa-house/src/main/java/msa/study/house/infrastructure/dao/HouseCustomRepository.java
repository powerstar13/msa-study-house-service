package msa.study.house.infrastructure.dao;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import msa.study.house.application.dto.HouseCommand;
import msa.study.house.domain.Rental;

import java.util.List;

public interface HouseCustomRepository {

    Mono<List<Integer>> getHouseIdListOfHousePage(HouseCommand.HousePage command);
    
    Flux<Rental> getRentalListOfHousePage(HouseCommand.HousePage command, List<Integer> houseIdList);
}
