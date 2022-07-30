package station3.assignment.member.domain.service;

import reactor.core.publisher.Mono;
import station3.assignment.member.application.dto.MemberCommand;
import station3.assignment.member.domain.service.dto.MemberDTO;

public interface MemberService {

    Mono<MemberDTO.MemberTokenInfo> memberRegister(MemberCommand.MemberRegister command);
}
