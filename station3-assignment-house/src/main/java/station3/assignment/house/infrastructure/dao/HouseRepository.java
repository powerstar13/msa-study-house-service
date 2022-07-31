package station3.assignment.house.infrastructure.dao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import station3.assignment.house.domain.House;

@Repository
public interface HouseRepository extends ReactiveCrudRepository<House, Integer> {

    Mono<House> findByHouseToken(String houseToken);

    Flux<House> findAllByMemberId(int memberId);
}
