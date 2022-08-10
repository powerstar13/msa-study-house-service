package msa.study.house.infrastructure.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import msa.study.house.domain.House;

import java.util.List;

@Repository
public interface HouseRepository extends ReactiveCrudRepository<House, Integer>, HouseCustomRepository {

    Mono<House> findByHouseToken(String houseToken);

    Flux<House> findAllByMemberId(int memberId);

    Flux<House> findAllByHouseIdIn(List<Integer>houseIdList, Pageable pageable);
}
