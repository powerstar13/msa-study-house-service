package station3.assignment.house.infrastructure.dao;

import reactor.core.publisher.Mono;
import station3.assignment.house.application.dto.HouseCommand;

import java.util.List;

public interface HouseCustomRepository {

    Mono<List<Integer>> getHouseIdListOfHousePage(HouseCommand.HousePage command);
}
