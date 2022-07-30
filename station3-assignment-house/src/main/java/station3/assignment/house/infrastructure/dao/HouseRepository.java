package station3.assignment.house.infrastructure.dao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import station3.assignment.house.domain.House;

@Repository
public interface HouseRepository extends ReactiveCrudRepository<House, Integer> {
}
