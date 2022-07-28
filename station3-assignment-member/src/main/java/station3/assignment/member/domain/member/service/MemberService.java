package station3.assignment.member.domain.member.service;

import reactor.core.publisher.Mono;
import station3.assignment.member.application.member.dto.MemberCommand;
import station3.assignment.member.domain.member.service.dto.MemberDTO;

public interface MemberService {

    Mono<MemberDTO.MemberTokenInfo> memberRegister(MemberCommand.MemberRegister command);
}
