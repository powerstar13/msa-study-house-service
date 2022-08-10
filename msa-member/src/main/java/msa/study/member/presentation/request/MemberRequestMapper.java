package msa.study.member.presentation.request;

import msa.study.member.application.dto.MemberCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MemberRequestMapper {
    
    MemberCommand.MemberRegister of(MemberRegisterRequest request);

    MemberCommand.MemberLogin of(MemberLoginRequest request);
}
