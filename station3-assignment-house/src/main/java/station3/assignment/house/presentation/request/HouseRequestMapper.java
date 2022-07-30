package station3.assignment.house.presentation.request;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import station3.assignment.house.application.dto.HouseCommand;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface HouseRequestMapper {
    
    @Mapping(source = "rentalRegisterRequestList", target = "rentalRegisterDTOList")
    HouseCommand.HouseRegister of(HouseRegisterRequest request);
}
