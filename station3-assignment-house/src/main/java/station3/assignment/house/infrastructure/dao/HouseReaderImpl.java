package station3.assignment.house.infrastructure.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import station3.assignment.house.domain.House;
import station3.assignment.house.domain.Rental;
import station3.assignment.house.domain.service.HouseReader;
import station3.assignment.house.domain.service.dto.HouseDTO;
import station3.assignment.house.domain.service.dto.HouseDTOMapper;
import station3.assignment.house.infrastructure.exception.status.ExceptionMessage;
import station3.assignment.house.infrastructure.exception.status.NotFoundDataException;

@Component
@RequiredArgsConstructor
public class HouseReaderImpl implements HouseReader {

    private final HouseRepository houseRepository;
    private final RentalRepository rentalRepository;
    private final HouseDTOMapper houseDTOMapper;

    /**
     * 방 정보 조회
     * @param houseToken: 방 대체 식별키
     * @return HouseAggregate: 방 애그리거트 정보
     */
    @Override
    public Mono<HouseDTO.HouseAggregate> findHouseAggregateInfo(String houseToken) {

        Mono<House> houseMono = houseRepository.findByHouseToken(houseToken) // 방 정보 조회
            .switchIfEmpty(Mono.error(new NotFoundDataException(ExceptionMessage.NotFoundHouse.getMessage())));

        return houseMono.flatMap(house -> {
            Flux<Rental> rentalFlux = rentalRepository.findByHouseId(house.getHouseId());

            return Mono.just(houseDTOMapper.of(house, rentalFlux));
        });
    }
}
