package station3.assignment.member.infrastructure.dao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;
import station3.assignment.member.domain.Member;
import station3.assignment.member.domain.MemberType;

@Repository
public interface MemberRepository extends ReactiveCrudRepository<Member, Integer> {
    
    Mono<Member> findByMemberLoginIdAndMemberType(String memberLoginId, MemberType memberType);

    Mono<Member> findByMemberToken(String memberToken);
}
