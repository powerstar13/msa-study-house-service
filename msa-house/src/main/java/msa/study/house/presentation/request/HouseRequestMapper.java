package msa.study.house.presentation.request;

import org.mapstruct.*;
import msa.study.house.application.dto.HouseCommand;

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

    HouseCommand.HousePage of(HousePageRequest request);
}
