package station3.assignment.house.infrastructure.dao;

import reactor.core.publisher.Flux;
import station3.assignment.house.application.dto.HouseCommand;
import station3.assignment.house.domain.House;

public interface HouseCustomRepository {

    Flux<House> housePage(HouseCommand.HousePage command);
}
