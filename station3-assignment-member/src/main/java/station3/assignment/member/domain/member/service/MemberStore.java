package station3.assignment.member.domain.member.service;

import reactor.core.publisher.Mono;
import station3.assignment.member.application.member.dto.MemberCommand;
import station3.assignment.member.domain.member.Member;

public interface MemberStore {

    Mono<Member> memberRegister(MemberCommand.MemberRegister command);
}
