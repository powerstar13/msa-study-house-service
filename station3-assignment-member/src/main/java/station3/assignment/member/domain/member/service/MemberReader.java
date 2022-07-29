package station3.assignment.member.domain.member.service;

import reactor.core.publisher.Mono;
import station3.assignment.member.application.member.dto.MemberCommand;

public interface MemberReader {
    
    Mono<Void> memberExistCheck(MemberCommand.MemberRegister command);
}
