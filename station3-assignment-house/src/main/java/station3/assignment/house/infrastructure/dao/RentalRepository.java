package station3.assignment.house.infrastructure.dao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import station3.assignment.house.domain.Rental;

@Repository
public interface RentalRepository extends ReactiveCrudRepository<Rental, Integer> {

    Flux<Rental> findByHouseId(int houseId);
}
