package msa.study.member.infrastructure.dao;

import msa.study.member.domain.Member;
import msa.study.member.domain.MemberType;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface MemberRepository extends ReactiveCrudRepository<Member, Integer> {
    
    Mono<Member> findByMemberLoginIdAndMemberType(String memberLoginId, MemberType memberType);

    Mono<Member> findByMemberToken(String memberToken);

    Mono<Member> findByMemberLoginId(String memberLoginId);
}
