package station3.assignment.house.domain.service.dto;

import org.mapstruct.*;
import reactor.core.publisher.Flux;
import station3.assignment.house.domain.House;
import station3.assignment.house.domain.Rental;

import java.util.List;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface HouseDTOMapper {

    HouseDTO.HouseAggregate of(House house, Flux<Rental> rentalFlux);

    HouseDTO.HouseInfo of(House house, List<Rental> rentalList);
}
