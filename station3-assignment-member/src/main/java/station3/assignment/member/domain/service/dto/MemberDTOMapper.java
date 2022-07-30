package station3.assignment.member.domain.service.dto;

import org.mapstruct.*;
import station3.assignment.member.domain.Member;

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
