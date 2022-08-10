package msa.study.house.domain.service.dto;

import org.mapstruct.*;
import reactor.core.publisher.Flux;
import msa.study.house.domain.House;
import msa.study.house.domain.Rental;

import java.util.List;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface HouseDTOMapper {

    HouseDTO.HouseAggregate of(House house, Flux<Rental> rentalFlux);

    HouseDTO.HouseInfo of(House house, List<Rental> rentalList);

    HouseDTO.HousePage of(HouseDTO.pageInfo pageInfo, List<HouseDTO.HouseInfo> houseList);
}
