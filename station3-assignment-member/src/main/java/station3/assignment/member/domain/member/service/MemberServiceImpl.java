package station3.assignment.member.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import station3.assignment.member.application.member.dto.MemberCommand;
import station3.assignment.member.domain.member.service.dto.MemberDTO;
import station3.assignment.member.domain.member.service.dto.MemberDTOMapper;

@Component
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberReader memberReader;
    private final MemberStore memberStore;
    private final MemberDTOMapper memberDTOMapper;

    @Override
    public Mono<MemberDTO.MemberTokenInfo> memberRegister(MemberCommand.MemberRegister command) {

        return memberReader.memberExistCheck(command) // 1. 이미 등록된 회원인지 확인
            .then(memberStore.memberRegister(command) // 2. 회원 등록
                .flatMap(member -> Mono.just(memberDTOMapper.of(member))) // 3. 회원 대체 식별키 반환
            );
    }
}
