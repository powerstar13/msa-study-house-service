package station3.assignment.house.infrastructure.dao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import station3.assignment.house.domain.Rental;

@Repository
public interface RentalRepository extends ReactiveCrudRepository<Rental, Integer> {
}
