package msa.study.house.application.dto;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface HouseCommandMapper {

    HouseCommand.ExchangedHouseRegister of(int memberId, HouseCommand.HouseRegister houseRegister);
}
