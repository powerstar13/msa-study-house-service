package station3.assignment.member.domain.member.service.dto;

import org.mapstruct.*;
import station3.assignment.member.application.member.dto.MemberCommand;
import station3.assignment.member.domain.member.Member;

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
}
