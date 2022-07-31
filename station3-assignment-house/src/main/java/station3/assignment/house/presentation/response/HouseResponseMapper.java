package station3.assignment.house.presentation.response;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import station3.assignment.house.domain.service.dto.HouseDTO;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface HouseResponseMapper {

    HouseRegisterResponse of(HouseDTO.HouseTokenInfo houseTokenInfo);

    HouseInfoResponse of(HouseDTO.HouseInfo houseInfo);
}
