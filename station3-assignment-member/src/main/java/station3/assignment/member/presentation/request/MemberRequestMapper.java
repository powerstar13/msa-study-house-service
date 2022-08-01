package station3.assignment.member.presentation.request;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import station3.assignment.member.application.dto.MemberCommand;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MemberRequestMapper {
    
    MemberCommand.MemberRegister of(MemberRegisterRequest request);

    MemberCommand.MemberLogin of(MemberLoginRequest request);
}
