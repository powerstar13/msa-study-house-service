package station3.assignment.member.presentation.response;

import org.mapstruct.*;
import station3.assignment.member.domain.service.dto.MemberDTO;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MemberResponseMapper {

    MemberRegisterResponse of(MemberDTO.MemberTokenInfo memberTokenInfo);

    ExchangeMemberTokenResponse of(MemberDTO.MemberIdInfo memberIdInfo);

    MemberLoginResponse of(MemberDTO.MemberLoginInfo memberLoginInfo);
}
