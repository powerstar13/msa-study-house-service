package msa.study.member.domain.service;

import reactor.core.publisher.Mono;
import msa.study.member.application.dto.MemberCommand;
import msa.study.member.domain.Member;

public interface MemberStore {

    Mono<Member> memberRegister(MemberCommand.MemberRegister command);
}
