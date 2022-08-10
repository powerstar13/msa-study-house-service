package msa.study.member.domain.service;

import msa.study.member.application.dto.MemberCommand;
import reactor.core.publisher.Mono;
import msa.study.member.domain.Member;
import msa.study.member.domain.service.dto.MemberDTO;

public interface MemberReader {
    
    Mono<Void> memberExistCheck(MemberCommand.MemberRegister command);

    Mono<MemberDTO.MemberIdInfo> exchangeMemberToken(String memberToken);

    Mono<Member> loginVerify(MemberCommand.MemberLogin command);
}
