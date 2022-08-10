package msa.study.member.domain.service;

import reactor.core.publisher.Mono;
import msa.study.member.application.dto.MemberCommand;
import msa.study.member.domain.service.dto.MemberDTO;

public interface MemberService {

    Mono<MemberDTO.MemberTokenInfo> memberRegister(MemberCommand.MemberRegister command);

    Mono<MemberDTO.MemberIdInfo> exchangeMemberToken(String memberToken);

    Mono<MemberDTO.MemberLoginInfo> login(MemberCommand.MemberLogin command);
}
