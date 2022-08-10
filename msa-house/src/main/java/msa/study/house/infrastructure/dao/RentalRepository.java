package msa.study.house.infrastructure.dao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import msa.study.house.domain.Rental;

@Repository
public interface RentalRepository extends ReactiveCrudRepository<Rental, Integer>, HouseCustomRepository {

    Flux<Rental> findAllByHouseId(int houseId);
}
