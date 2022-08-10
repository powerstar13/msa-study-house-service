package msa.study.member.domain.service;

import lombok.RequiredArgsConstructor;
import msa.study.member.application.dto.MemberCommand;
import msa.study.member.domain.service.dto.MemberDTO;
import msa.study.member.infrastructure.jwt.factory.TokenStore;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import msa.study.member.domain.service.dto.MemberDTOMapper;

@Component
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberReader memberReader;
    private final MemberStore memberStore;
    private final MemberDTOMapper memberDTOMapper;
    private final TokenStore tokenStore;

    /**
     * 회원 등록 처리
     * @param command: 등록할 회원 정보
     * @return MemberTokenInfo: 회원 토큰 정보
     */
    @Override
    public Mono<MemberDTO.MemberTokenInfo> memberRegister(MemberCommand.MemberRegister command) {

        return memberReader.memberExistCheck(command) // 1. 이미 등록된 회원인지 확인
            .then(memberStore.memberRegister(command) // 2. 회원 등록
                .flatMap(member -> {
                    String accessToken = tokenStore.tokenPublish(member.getMemberToken(), true); // 액세스토큰 생성
                    String refreshToken = tokenStore.tokenPublish(member.getMemberToken(), false); // 리프레시토큰 생성

                    MemberDTO.MemberTokenInfo memberTokenInfo = memberDTOMapper.of(member); // 3. 회원 대체 식별키 전달
                    memberTokenInfo.jwtRegister(accessToken, refreshToken); // JWT 전달

                    return Mono.just(memberTokenInfo);
                })
            );
    }

    /**
     * 회원 고유번호 가져오기
     * @param memberToken: 회원 대체 식별키
     * @return MemberIdInfo: 회원 고유번호
     */
    @Override
    public Mono<MemberDTO.MemberIdInfo> exchangeMemberToken(String memberToken) {
        return memberReader.exchangeMemberToken(memberToken);
    }

    /**
     * 회원 로그인 처리
     * @param command: 로그인 정보
     * @return MemberLoginInfo: 로그인 회원 정보
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<MemberDTO.MemberLoginInfo> login(MemberCommand.MemberLogin command) {
        // 1. 로그인 검증
        return memberReader.loginVerify(command)
            .flatMap(member -> {
                // 2. JWT 토큰 발행
                String accessToken = tokenStore.tokenPublish(member.getMemberToken(), true);
                String refreshToken = tokenStore.tokenPublish(member.getMemberToken(), false);

                return Mono.just(memberDTOMapper.of(member, accessToken, refreshToken));
            });
    }
}
