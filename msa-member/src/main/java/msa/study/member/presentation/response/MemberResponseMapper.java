package msa.study.member.presentation.response;

import org.mapstruct.*;
import msa.study.member.domain.service.dto.MemberDTO;

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
