package station3.assignment.member.presentation.response;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import station3.assignment.member.domain.service.dto.MemberDTO;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MemberResponseMapper {

    MemberRegisterResponse of(MemberDTO.MemberTokenInfo memberTokenInfo);
}
