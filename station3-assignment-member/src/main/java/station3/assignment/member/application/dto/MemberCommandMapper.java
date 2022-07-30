package station3.assignment.member.application.dto;

import org.mapstruct.*;
import station3.assignment.member.domain.Member;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MemberCommandMapper {
    
    @Mappings({
        @Mapping(target = "createdAt", ignore = true),
        @Mapping(target = "updatedAt", ignore = true),
        @Mapping(target = "memberId", ignore = true),
        @Mapping(target = "memberToken", ignore = true)
    })
    Member of(MemberCommand.MemberRegister memberRegister);
}
