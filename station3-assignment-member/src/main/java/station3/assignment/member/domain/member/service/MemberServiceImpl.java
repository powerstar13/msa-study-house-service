package station3.assignment.member.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import station3.assignment.member.application.member.dto.MemberCommand;
import station3.assignment.member.domain.member.service.dto.MemberDTO;
import station3.assignment.member.domain.member.service.dto.MemberDTOMapper;
import station3.assignment.member.infrastructure.jwt.factory.TokenStore;

@Component
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberReader memberReader;
    private final MemberStore memberStore;
    private final MemberDTOMapper memberDTOMapper;
    private final TokenStore tokenStore;

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
}
