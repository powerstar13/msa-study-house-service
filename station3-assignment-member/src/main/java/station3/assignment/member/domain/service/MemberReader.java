package station3.assignment.member.domain.service;

import reactor.core.publisher.Mono;
import station3.assignment.member.application.dto.MemberCommand;
import station3.assignment.member.domain.Member;
import station3.assignment.member.domain.service.dto.MemberDTO;

public interface MemberReader {
    
    Mono<Void> memberExistCheck(MemberCommand.MemberRegister command);

    Mono<MemberDTO.MemberIdInfo> exchangeMemberToken(String memberToken);

    Mono<Member> loginVerify(MemberCommand.MemberLogin command);
}
