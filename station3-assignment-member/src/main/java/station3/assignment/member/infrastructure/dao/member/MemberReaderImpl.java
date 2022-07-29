package station3.assignment.member.infrastructure.dao.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import station3.assignment.member.application.member.dto.MemberCommand;
import station3.assignment.member.domain.member.service.MemberReader;
import station3.assignment.member.infrastructure.exception.status.AlreadyDataException;
import station3.assignment.member.infrastructure.exception.status.ExceptionMessage;

@Component
@RequiredArgsConstructor
public class MemberReaderImpl implements MemberReader {

    private final MemberRepository memberRepository;

    /**
     * 이미 존재하는 회원인지 확인
     * @param command: 회원 정보
     * @return Void: 조회된 회원 없음
     */
    @Override
    public Mono<Void> memberExistCheck(MemberCommand.MemberRegister command) {

        return memberRepository.findByMemberLoginIdAndMemberType(command.getMemberLoginId(), command.getMemberType())
            .hasElement()
            .flatMap(alreadyMember -> {
                if (alreadyMember) return Mono.error(new AlreadyDataException(ExceptionMessage.AlreadyDataMember.getMessage()));

                return Mono.empty();
            });
    }
}
