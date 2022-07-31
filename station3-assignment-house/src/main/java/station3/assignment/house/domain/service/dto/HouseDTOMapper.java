package station3.assignment.house.domain.service.dto;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import reactor.core.publisher.Flux;
import station3.assignment.house.domain.House;
import station3.assignment.house.domain.Rental;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface HouseDTOMapper {

    HouseDTO.HouseAggregate of(House house, Flux<Rental> rentalFlux);
}
