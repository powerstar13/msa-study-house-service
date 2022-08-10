package msa.study.member.application;

import lombok.RequiredArgsConstructor;
import msa.study.member.domain.service.MemberService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import msa.study.member.application.dto.MemberCommand;
import msa.study.member.domain.service.dto.MemberDTO;

@Component
@RequiredArgsConstructor
public class MemberFacade {
    
    private final MemberService memberService;
    
    /**
     * 회원 등록
     * @param command: 등록할 회원 정보
     * @return MemberTokenInfo: 회원 대체 식별키
     */
    public Mono<MemberDTO.MemberTokenInfo> memberRegister(MemberCommand.MemberRegister command) {
        return memberService.memberRegister(command); // 회원 등록 처리
    }

    /**
     * 회원 고유번호 가져오기
     * @param memberToken: 회원 대체 식별키
     * @return MemberIdInfo: 회원 고유번호
     */
    public Mono<MemberDTO.MemberIdInfo> exchangeMemberToken(String memberToken) {
        return memberService.exchangeMemberToken(memberToken); // 회원 고유번호 가져오기 처리
    }

    /**
     * 회원 로그인
     * @param command: 로그인 정보
     * @return MemberLoginInfo: 로그인 회원 정보
     */
    public Mono<MemberDTO.MemberLoginInfo> login(MemberCommand.MemberLogin command) {
        return memberService.login(command); // 회원 로그인 처리
    }
}
