package msa.study.member.domain.service.dto;

import msa.study.member.domain.Member;
import org.mapstruct.*;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MemberDTOMapper {

    @Mappings({
        @Mapping(target = "accessToken", ignore = true),
        @Mapping(target = "refreshToken", ignore = true)
    })
    MemberDTO.MemberTokenInfo of(Member member);

    MemberDTO.MemberLoginInfo of(Member member, String accessToken, String refreshToken);
}
