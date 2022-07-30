package station3.assignment.member.domain.service;

import reactor.core.publisher.Mono;
import station3.assignment.member.application.dto.MemberCommand;

public interface MemberReader {
    
    Mono<Void> memberExistCheck(MemberCommand.MemberRegister command);
}
