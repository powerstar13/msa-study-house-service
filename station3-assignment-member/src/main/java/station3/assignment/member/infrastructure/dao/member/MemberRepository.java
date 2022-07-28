package station3.assignment.member.infrastructure.dao.member;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import station3.assignment.member.domain.member.Member;
import station3.assignment.member.domain.member.MemberType;

@Repository
public interface MemberRepository extends ReactiveCrudRepository<Member, Integer> {
    
    Mono<Member> findByMemberLoginIdAndMemberType(String memberLoginId, MemberType memberType);
}
