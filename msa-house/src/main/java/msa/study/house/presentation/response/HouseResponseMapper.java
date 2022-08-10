package msa.study.house.presentation.response;

import msa.study.house.domain.service.dto.HouseDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface HouseResponseMapper {

    HouseRegisterResponse of(HouseDTO.HouseTokenInfo houseTokenInfo);

    HouseInfoResponse of(HouseDTO.HouseInfo houseInfo);

    HouseListResponse of(HouseDTO.HouseList houseList);

    HousePageResponse of(HouseDTO.HousePage housePage);
}
