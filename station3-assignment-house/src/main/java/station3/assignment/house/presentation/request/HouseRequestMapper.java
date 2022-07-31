package station3.assignment.house.presentation.request;

import org.mapstruct.*;
import station3.assignment.house.application.dto.HouseCommand;

import java.util.Map;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface HouseRequestMapper {

    HouseCommand.HouseRegister of(HouseRegisterRequest request);

    HouseCommand.HouseModify of(HouseModifyRequest request);

    HousePageRequest of(Map<String, String> params);

    HouseCommand.HouseDTOPage of(HousePageRequest request);
}
