package station3.assignment.member.domain.service;

import reactor.core.publisher.Mono;
import station3.assignment.member.application.dto.MemberCommand;
import station3.assignment.member.domain.Member;

public interface MemberStore {

    Mono<Member> memberRegister(MemberCommand.MemberRegister command);
}
